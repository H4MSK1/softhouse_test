import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException

class PersonDataParser {

    @Throws(IOException::class)
    fun parse(fileName: String, outputPath: String) {
        readPersonDataFromFile(fileName, outputPath)
        println("Person data has successfully been migrated to output file: $outputPath")
    }

    @Throws(IOException::class)
    private fun readPersonDataFromFile(path: String, outputPath: String) {
        FileUtils.lineIterator(File(path), "UTF-8").use { it ->
            val personCollection = PersonCollection()

            while (it.hasNext()) {
                val line: String = it.nextLine()

                when (line[0]) {
                    'P' -> personCollection.push(line, PersonCollection.ElementType.P)
                    'T' -> personCollection.push(line, PersonCollection.ElementType.T)
                    'A' -> personCollection.push(line, PersonCollection.ElementType.A)
                    'F' -> personCollection.push(line, PersonCollection.ElementType.F)
                }
            }

            XMLDocumentBuilder().savePersonsAsXML(outputPath, personCollection.convertNodesToPersonObjects())
        }
    }
}
