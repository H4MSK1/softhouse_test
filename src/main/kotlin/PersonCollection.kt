class PersonCollection {
    class ElementNode(var value: String, var type: ElementType)

    enum class ElementType {
        P, T, A, F
    }

    private val nodes = mutableListOf<ElementNode>()

    fun push(value: String, type: ElementType) {
        nodes.add(ElementNode(value, type))
    }

    fun convertNodesToPersonObjects(): MutableList<Person> {
        val personsList = mutableListOf<Person>()
        var person: Person? = null
        for (node: ElementNode in this.nodes) {
            if (node.type == ElementType.P) {
                person = Person()
                personsList.add(person)
            }
            person?.parseNode(node)
        }

        nodes.clear()
        return personsList
    }
}
