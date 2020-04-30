const val DEFAULT_FILE_SOURCE = "person_data.txt";
const val OUTPUT_FILE_SOURCE = "output.xml";

fun main(args: Array<String>) {
    val fileName = args.getOrElse(0){ DEFAULT_FILE_SOURCE}
    PersonDataParser().parse(fileName, OUTPUT_FILE_SOURCE)
}
