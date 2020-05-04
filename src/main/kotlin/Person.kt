class Person {
    inner class Adress(fragments: Array<String>) {
        var street = getOrDefault(fragments, 1)
        var city = getOrDefault(fragments, 2)
        var zipcode = getOrDefault(fragments, 3)
    }

    inner class PhoneNumber(fragments: Array<String>) {
        var mobile = getOrDefault(fragments, 1)
        var landline = getOrDefault(fragments, 2)
    }

    inner class Family(fragments: Array<String>) {
        var name = getOrDefault(fragments, 1)
        var born = getOrDefault(fragments, 2)
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
                firstname = getOrDefault(fragments, 1)
                lastname = getOrDefault(fragments, 2)
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
