//package orm.migrations
//
//import java.io.File
//import java.nio.file.Files
//import java.nio.file.Paths
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//
//class Creator(private val directory: String) {
//    companion object {
//        private val TEMPLATE = """
//            package orm.migration
//
//            import orm.migrations.Migration
//
//            class MigrationClassName : Migration {
//                fun up() = \"\"\"
//                    # query
//                \"\"\".trimIndent();
//
//                fun down() = \"\"\"
//                    # query
//                \"\"\".trimIndent();
//            }
//        """.trimIndent()
//    }
//
//    fun create(namespace: String, name: String) {
//        val filename = name.replaceFirstChar { it.lowercase() }
//            .split(Regex("(?=[A-Z])"))
//            .joinToString("_")
//            .lowercase()
//
//        val clazz = name
//            .split("_")
//            .joinToString("") { it.ucFirst() }
//
//        val datetime = LocalDateTime.now()
//            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss"))
//
//        val content = TEMPLATE
//            .replace("MigrationClassName", clazz)
//            .replace("\\\"", "\"")
//
//        Files.createDirectories(Paths.get("${directory}/${namespace}"))
//        File("${directory}/${namespace}/${datetime}_${filename}.kt").writeText(content)
//    }
//}
//
//fun main() {
//    val path = Paths.get("").toAbsolutePath().toString()
//
//    val creator = Creator("$path/orm/.migrations")
//    creator.create("payment", "create_payment_tables")
//}
