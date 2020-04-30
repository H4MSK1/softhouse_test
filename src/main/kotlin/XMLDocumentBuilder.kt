import Person.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class XMLDocumentBuilder {
    private fun buildPhoneXMLNode(document: Document, phoneNumber: PhoneNumber): Element {
        val root = document.createElement("phone")
        root.appendChild(buildXMLNodeFromValue(document, "mobile", phoneNumber.mobile))
        root.appendChild(buildXMLNodeFromValue(document, "landline", phoneNumber.landline))
        return root
    }

    private fun buildAdressXMLNode(document: Document, adress: Adress): Element {
        val root = document.createElement("adress")
        root.appendChild(buildXMLNodeFromValue(document, "street", adress.street))
        root.appendChild(buildXMLNodeFromValue(document, "city", adress.city))
        if (adress.zipcode.isNotEmpty()) {
            root.appendChild(buildXMLNodeFromValue(document, "zipcode", adress.zipcode))
        }
        return root
    }

    private fun buildFamilyXMLNode(document: Document, root: Element, familyList: List<Family>) {
        for (familyMember in familyList) {
            val familyRoot = document.createElement("family")
            familyRoot.appendChild(buildXMLNodeFromValue(document, "name", familyMember.name))
            familyRoot.appendChild(buildXMLNodeFromValue(document, "born", familyMember.born))

            when {
                familyMember.adress != null ->
                    familyRoot.appendChild(buildAdressXMLNode(document, familyMember.adress!!))
                familyMember.phoneNumber != null ->
                    familyRoot.appendChild(buildPhoneXMLNode(document, familyMember.phoneNumber!!))
            }

            root.appendChild(familyRoot)
        }
    }

    private fun buildXMLNodeFromValue(document: Document, attribute: String, attributeValue: String): Element {
        val attributeNode = document.createElement(attribute)
        attributeNode.appendChild(document.createTextNode(attributeValue))
        return attributeNode
    }

    fun savePersonsAsXML(xmlOutputFilePath: String, personCollection: List<Person>) = try {
        val document = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .newDocument()

        val root = document.createElement("people")
        document.appendChild(root)

        for (personObj in personCollection) {
            val personRoot = document.createElement("person")
            personRoot.appendChild(buildXMLNodeFromValue(document, "firstname", personObj.firstname))
            personRoot.appendChild(buildXMLNodeFromValue(document, "lastname", personObj.lastname))

            if (personObj.personAdress != null) {
                personRoot.appendChild(buildAdressXMLNode(document, personObj.personAdress!!))
            }
            if (personObj.personPhoneNumber != null) {
                personRoot.appendChild(buildPhoneXMLNode(document, personObj.personPhoneNumber!!))
            }
            if (personObj.hasFamily()) {
                buildFamilyXMLNode(document, personRoot, personObj.familyList)
            }

            root.appendChild(personRoot)
        }

        val transformer: Transformer = TransformerFactory
            .newInstance()
            .newTransformer()

        transformer.transform(
            DOMSource(document),
            StreamResult(File(xmlOutputFilePath))
        )
    } catch (err: ParserConfigurationException) {
        err.printStackTrace()
    } catch (err: TransformerException) {
        err.printStackTrace()
    }
}
