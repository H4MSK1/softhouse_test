import Person.*
import javax.xml.stream.XMLStreamWriter

class XMLDocumentBuilder {

    private fun writeNodeToBuffer(writer: XMLStreamWriter, attribute: String, value: String) {
        writer.writeStartElement(attribute)
        writer.writeCharacters(value)
        writer.writeEndElement()
    }

    private fun writePersonFamilyToBuffer(writer: XMLStreamWriter, family: List<Family>) {
        for (familyMember in family) {
            writer.writeStartElement("family")
            writeNodeToBuffer(writer, "name", familyMember.name)
            writeNodeToBuffer(writer, "born", familyMember.born)

            if (familyMember.adress != null) {
                writePersonAddressToBuffer(writer, familyMember.adress!!)
            }

            if (familyMember.phoneNumber != null) {
                writePersonPhoneNumberToBuffer(writer, familyMember.phoneNumber!!)
            }

            writer.writeEndElement()
        }
    }

    private fun writePersonAddressToBuffer(writer: XMLStreamWriter, adress: Adress) {
        writer.writeStartElement("address")
        writeNodeToBuffer(writer, "street", adress.street)
        writeNodeToBuffer(writer, "city", adress.city)
        writeNodeToBuffer(writer, "zipcode", adress.zipcode)
        writer.writeEndElement()
    }

    private fun writePersonPhoneNumberToBuffer(writer: XMLStreamWriter, phoneNumber: PhoneNumber) {
        writer.writeStartElement("phone")
        writeNodeToBuffer(writer, "mobile", phoneNumber.mobile)
        writeNodeToBuffer(writer, "landline", phoneNumber.landline)
        writer.writeEndElement()
    }

    fun transformPersonNodeToXML(writer: XMLStreamWriter, personCollection: List<Person>) {
        val iterator = personCollection.listIterator() as MutableListIterator<Person>
        while (iterator.hasNext()) {
            val person = iterator.next()

            writer.writeStartElement("person")
            writeNodeToBuffer(writer, "firstname", person.firstname)
            writeNodeToBuffer(writer, "lastname", person.lastname)

            if (person.personAdress != null) {
                writePersonAddressToBuffer(writer, person.personAdress!!)
            }

            if (person.personPhoneNumber != null) {
                writePersonPhoneNumberToBuffer(writer, person.personPhoneNumber!!)
            }

            if (person.hasFamily()) {
                writePersonFamilyToBuffer(writer, person.familyList)
            }

            writer.writeEndElement()
            iterator.remove()
        }
    }
}
