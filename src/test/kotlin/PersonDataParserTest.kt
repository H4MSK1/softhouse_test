import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.io.IOException

class PersonDataParserTest {

    @Test
    fun parseNonExistingSourceFile() {
        assertThrows<IOException> {
            PersonDataParser().parse("i dont exist", "")
        }
    }

    @Test
    fun parseFromSourceFile() {
        val classLoader = javaClass.classLoader
        val sourceFile = File(classLoader.getResource("person_data.txt").file).path
        val outputDestination = File(classLoader.getResource("output.xml").file).path

        assertDoesNotThrow {
            val parser = PersonDataParser()
            parser.parse(sourceFile, outputDestination)
        }
    }
}
