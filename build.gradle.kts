import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jUnitVersion: String by project
val mockkVersion: String by project
val postgresVersion: String by project
val sqliteVersion: String by project
val kotlinLoggingVersion: String by project
val jacksonVersion: String by project
val coroutinesVersion: String by project

object Publication {
    const val group = "io.tcds"
    val buildVersion: String = System.getenv("VERSION") ?: "dev"

    object Sonatype {
        val user: String = System.getenv("OSS_USER") ?: ""
        val password: String = System.getenv("OSS_PASSWORD") ?: ""
    }

    object Github {
        val user: String = System.getenv("GPR_USERNAME") ?: ""
        val token: String = System.getenv("GPR_TOKEN") ?: ""
    }

    object Gpg {
        val signingKeyId: String = System.getenv("GPG_KEY_ID") ?: ""
        val signingKey: String = System.getenv("GPG_KEY") ?: ""
        val signingPassword: String = System.getenv("GPG_KEY_PASSWORD") ?: ""
    }
}

plugins {
    application
    `kotlin-dsl`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
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
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

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

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
val javadocJar by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            group = Publication.group
            version = Publication.buildVersion
            url = uri("https://maven.pkg.github.com/tcds-io/kotlin-orm")

            credentials {
                username = Publication.Github.user
                password = Publication.Github.token
            }
        }

        maven {
            name = "SonaType"
            group = Publication.group
            version = Publication.buildVersion
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = Publication.Sonatype.user
                password = Publication.Sonatype.password
            }
        }
    }

    /**
     * references:
     * - https://docs.gradle.org/current/userguide/publishing_maven.html
     * - https://github.com/LukasForst/ktor-plugins/blob/master/build.gradle.kts
     * - https://devanshuchandra.medium.com/maven-central-publishing-using-gradle-and-gpg-signing-47b216179dd6
     */
    publications {
        listOf("defaultMavenJava", "pluginMaven").forEach { publication ->

            create<MavenPublication>(publication) {
                // from(components["java"])
                artifact(sourcesJar)
                artifact(javadocJar)

                pom {
                    name.set("Kotlin Simple ORM")
                    description.set("Kotlin Simple ORM is a simple but powerful database orm for kotlin applications")
                    url.set("https://github.com/tcds-io/kotlin-orm")
                    packaging = "jar"

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://github.com/tcds-io/kotlin-orm/blob/main/LICENSE")
                        }
                    }

                    developers {
                        developer {
                            id.set("tcds-io")
                            name.set("Thiago Cordeiro")
                            email.set("thiago@tcds.io")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com:tcds-io/kotlin-orm.git")
                        url.set("https://github.com/tcds-io/kotlin-orm")
                    }
                }
            }
        }
    }
}

signing {
    val signingKeyId = Publication.Gpg.signingKeyId
    val signingKey = Publication.Gpg.signingKey
    val signingPassword = Publication.Gpg.signingPassword

    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications["pluginMaven"])
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(Publication.Sonatype.user)
            password.set(Publication.Sonatype.password)
        }
    }
}
