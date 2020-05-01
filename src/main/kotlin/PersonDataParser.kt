import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLStreamWriter

class PersonDataParser {

    fun parse(fileName: String, outputPath: String) = measureExecutionTime({ timeElapsed ->
        println("Total execution time: $timeElapsed milliseconds")
    }) {
        println("Reading person data from source: $fileName")
        readPersonDataFromFile(fileName, outputPath)
        println("Person data has successfully been migrated to output file: $outputPath")
    }

    private fun processChunk(
        writer: XMLStreamWriter,
        xmlDocBuilder: XMLDocumentBuilder,
        personCollection: PersonCollection,
        chunkCounter: Int,
        personCounter: Int
    ) {
        println("Processing chunk number ($chunkCounter) with ($personCounter) person nodes" + System.lineSeparator())
        xmlDocBuilder.transformPersonNodeToXML(writer, personCollection.convertNodesToPersonObjects())
        writer.flush()
    }

    @Throws(IOException::class)
    private fun readPersonDataFromFile(path: String, outputPath: String) {
        FileUtils.lineIterator(File(path), "UTF-8").use { iterator ->
            val xmlDocBuilder = XMLDocumentBuilder()
            val writer: XMLStreamWriter = IndentingXMLStreamWriter(
                XMLOutputFactory
                    .newInstance()
                    .createXMLStreamWriter(FileOutputStream(outputPath), "UTF-8")
            )
            writer.writeStartDocument("UTF-8", "1.0")
            writer.writeStartElement("people")

            try {
                val personCollection = PersonCollection()
                val chunkSize = 100

                var chunkCounter = 1
                var personNodeCounter = 0
                var shouldProcessChunk = false

                while (iterator.hasNext()) {
                    val line: String = iterator.nextLine().trim()
                    if (line.isEmpty() || line.length < 2 || line[1] != '|') {
                        continue
                    }

                    when (line[0]) {
                        'P' -> {
                            if (shouldProcessChunk) {
                                processChunk(writer, xmlDocBuilder, personCollection, chunkCounter, personNodeCounter)
                                chunkCounter++
                                personNodeCounter = 1
                            } else {
                                personNodeCounter++
                            }

                            personCollection.push(line, PersonCollection.ElementType.P)
                        }
                        'T' -> personCollection.push(line, PersonCollection.ElementType.T)
                        'A' -> personCollection.push(line, PersonCollection.ElementType.A)
                        'F' -> personCollection.push(line, PersonCollection.ElementType.F)
                    }

                    shouldProcessChunk = personNodeCounter == chunkSize
                }

                if (personNodeCounter > 0) {
                    processChunk(writer, xmlDocBuilder, personCollection, chunkCounter, personNodeCounter)
                }
            } catch (err: Exception) {
                err.printStackTrace()
            } finally {
                writer.writeEndElement()
                writer.close()
            }
        }
    }
}
