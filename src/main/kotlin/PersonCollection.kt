import java.util.*

class PersonCollection {
    class ElementNode(var value: String, var type: ElementType)

    enum class ElementType {
        P, T, A, F
    }

    private val nodes = Stack<ElementNode>()

    fun push(value: String, type: ElementType) {
        nodes.push(ElementNode(value, type))
    }

    fun convertNodesToPersonObjects(): MutableList<Person> {
        val personsList = mutableListOf<Person>()
        lateinit var person: Person
        for (node: ElementNode in this.nodes) {
            if (node.type == ElementType.P) {
                personsList.add(Person())
                person = personsList[personsList.lastIndex]
            }
            person.parseNode(node);
        }

        nodes.clear()
        return personsList
    }
}
