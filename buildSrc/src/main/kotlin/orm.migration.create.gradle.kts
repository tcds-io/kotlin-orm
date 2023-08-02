import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val ormMigrationDirectory: String by project
val jacksonVersion = "2.14.0"

plugins {
    kotlin("jvm")
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
}

// ./gradlew migration-create -Pmigration=foo
tasks.register("migration-create") {
    if (false == project.hasProperty("migration")) {
        println("Missing migration name. Please run the command with `-Pmigration={name}` argument")

        return@register
    }

    val pattern = "(?<=.)[A-Z]".toRegex()
    val migration: String = (project.properties["migration"] as String).replace(pattern, "_$0").toLowerCase()
    val now = LocalDateTime.now()
    val format = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss")
    val name = "${now.format(format)}_$migration"

    val dir = File(ormMigrationDirectory)
    if (!dir.exists()) dir.mkdirs()

    val content = """
        migration:
          name: $name
          up: |
            CREATE TABLE ...
          down: |
            DROP TABLE ...
    """.trimIndent()

    File("$ormMigrationDirectory/$name.yaml").bufferedWriter().use { out -> out.write(content) }
}
