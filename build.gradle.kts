import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jUnitVersion: String by project
val mockkVersion: String by project
val postgresVersion: String by project
val sqliteVersion: String by project
val kotlinLoggingVersion: String by project

val buildVersion = System.getenv("VERSION") ?: "dev"

plugins {
    application
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    gradleApi()
    api(kotlin("stdlib"))

    // implementation("org.postgresql:postgresql:$postgresVersion")
    implementation("org.xerial:sqlite-jdbc:$sqliteVersion")

    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")

    testImplementation("org.junit.jupiter:junit-jupiter:$jUnitVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        languageVersion = "1.7"
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}
tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events("started", "skipped", "passed", "failed")
        showExceptions = true
        showCauses = true
        showStackTraces = true
        showStandardStreams = true
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            group = "io.tcds"
            version = buildVersion
            url = uri("https://maven.pkg.github.com/tcds-io/kotlin-orm")

            credentials {
                username = System.getenv("GPR_USERNAME")
                password = System.getenv("GPR_TOKEN")
            }
        }
    }
}
