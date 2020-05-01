class Person {
    inner class Adress(fragments: Array<String>) {
        var street = fragments.getOrElse(1) { "" }
        var city = fragments.getOrElse(2) { "" }
        var zipcode = fragments.getOrElse(3) { "" }
    }

    inner class PhoneNumber(fragments: Array<String>) {
        var mobile = fragments.getOrElse(1) { "" }
        var landline = fragments.getOrElse(2) { "" }
    }

    inner class Family(fragments: Array<String>) {
        var name = fragments.getOrElse(1) { "" }
        var born = fragments.getOrElse(2) { "" }
        var adress: Adress? = null
        var phoneNumber: PhoneNumber? = null
    }

    lateinit var firstname: String
    lateinit var lastname: String
    var personAdress: Adress? = null
    var personPhoneNumber: PhoneNumber? = null
    var familyList = mutableListOf<Family>()

    fun parseNode(node: PersonCollection.ElementNode) {
        val elementType = node.type
        val fragments = node.value.split("|").toTypedArray()

        when (elementType) {
            PersonCollection.ElementType.F -> familyList.add(Family(fragments))

            PersonCollection.ElementType.P -> {
                firstname = fragments.getOrElse(1) { "" }
                lastname = fragments.getOrElse(2) { "" }
            }

            PersonCollection.ElementType.T -> when {
                personPhoneNumber == null -> personPhoneNumber = PhoneNumber(fragments)
                hasFamily() -> currentFamily().phoneNumber = PhoneNumber(fragments)
            }

            PersonCollection.ElementType.A -> when {
                personAdress == null -> personAdress = Adress(fragments)
                hasFamily() -> currentFamily().adress = Adress(fragments)
            }
        }
    }

    fun currentFamily(): Family = familyList[familyList.lastIndex]
    fun hasFamily(): Boolean = familyList.count() > 0
}
