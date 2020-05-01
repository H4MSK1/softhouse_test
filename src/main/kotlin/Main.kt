import java.io.IOException

const val DEFAULT_FILE_SOURCE = "person_data.txt"
const val OUTPUT_FILE_SOURCE = "output.xml"

fun main(args: Array<String>) {
    val source = args.getOrElse(0) { DEFAULT_FILE_SOURCE }
    val destination = args.getOrElse(1) { OUTPUT_FILE_SOURCE }

    try {
        PersonDataParser().parse(source, destination)
    } catch (err: IOException) {
        err.printStackTrace()
    }
}

fun <T> measureExecutionTime(
    next: (Long) -> Unit,
    function: () -> T
) {
    val startTime = System.currentTimeMillis()
    function.invoke()
    next.invoke(System.currentTimeMillis() - startTime)
}
