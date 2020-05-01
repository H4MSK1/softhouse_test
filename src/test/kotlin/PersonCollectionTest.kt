import PersonCollection.ElementType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PersonCollectionTest {

    @Test
    fun shouldBuildTwoPersonObjects() {
        val personCollection = PersonCollection()
        personCollection.push("hello im a person", ElementType.P)
        personCollection.push("hello", ElementType.A)
        personCollection.push("hello", ElementType.T)
        personCollection.push("hello im a person", ElementType.P)

        assertEquals(2, personCollection.convertNodesToPersonObjects().count())
    }
}
