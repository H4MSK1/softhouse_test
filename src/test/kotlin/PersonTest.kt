import PersonCollection.ElementNode
import PersonCollection.ElementType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PersonTest {

    @Test
    fun personParseNameNode() {
        val firstname = "John"
        val lastname = "Doe"
        val node = ElementNode("P|$firstname|$lastname", ElementType.P)
        val person = Person()

        person.parseNode(node)

        assertEquals(firstname, person.firstname)
        assertEquals(lastname, person.lastname)
    }

    @Test
    fun personParsePhoneNumberNode() {
        val mobile = "0768-101801"
        val landline = "08-101801"
        val node = ElementNode("T|$mobile|$landline", ElementType.T)
        val person = Person()

        assertNull(person.personPhoneNumber)

        person.parseNode(node)

        assertNotNull(person.personPhoneNumber)
        assertEquals(mobile, person.personPhoneNumber?.mobile)
        assertEquals(landline, person.personPhoneNumber?.landline)
    }

    @Test
    fun personParseAddressNode() {
        val street = "Drottningholms slott"
        val city = "Stockholm"
        val zipcode = "10001"
        val node = ElementNode("A|$street|$city|$zipcode", ElementType.A)
        val person = Person()

        assertNull(person.personAdress)

        person.parseNode(node)

        assertNotNull(person.personAdress)
        assertEquals(street, person.personAdress?.street)
        assertEquals(city, person.personAdress?.city)
        assertEquals(zipcode, person.personAdress?.zipcode)
    }

    @Test
    fun personParseFamilyNode() {
        val name = "Victoria"
        val born = "1977"
        val node = ElementNode("F|$name|$born", ElementType.F)
        val person = Person()

        assertFalse(person.hasFamily())

        person.parseNode(node)

        assertTrue(person.hasFamily())
        assertEquals(name, person.currentFamily().name)
        assertEquals(born, person.currentFamily().born)
        assertNull(person.currentFamily().phoneNumber)
        assertNull(person.currentFamily().adress)
    }
}
