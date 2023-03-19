package io.tcds.orm.driver.sqlite

import io.tcds.orm.driver.Connection
import io.tcds.orm.driver.SqliteConnection
import mu.KotlinLogging
import org.gradle.internal.impldep.org.jetbrains.annotations.MustBeInvokedByOverriders
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

open class TestCase {
    private var connection: Connection? = null

    @BeforeEach
    @MustBeInvokedByOverriders
    open fun setup() {
        connection().execute("""
            CREATE TABLE addresses
            (
                id         VARCHAR(255) NOT NULL,
                street     VARCHAR(255) NOT NULL,
                number     VARCHAR(255) NOT NULL,
                main       TINYINT      NOT NULL,
                created_at DATETIME     NOT NULL
            );
        """.trimIndent())

        connection().execute("""
            CREATE TABLE users (
                id         VARCHAR(255) NOT NULL,
                name       VARCHAR(255) NOT NULL,
                email      VARCHAR(255) NOT NULL,
                height     FLOAT        NOT NULL,
                age        INT          NOT NULL,
                active     TINYINT      NOT NULL,
                address_id VARCHAR(255) NOT NULL
            );
        """.trimIndent())

        connection().execute("""
            CREATE TABLE user_status (
                user_id    VARCHAR(255) NOT NULL,
                status     VARCHAR(255) NOT NULL,
                at         DATETIME     NOT NULL
            );
        """.trimIndent())

        connection().execute("""
            CREATE TABLE user_address (
                user_id    VARCHAR(255) NOT NULL,
                address    TEXT NOT NULL
            );
        """.trimIndent()
        )
    }

    @AfterEach
    @MustBeInvokedByOverriders
    open fun tearDown() {
        connection().execute("DELETE FROM addresses")
        connection().execute("DELETE FROM users")
        connection().readWrite.close()
        connection = null
    }

    fun connection(): Connection {
        if (connection === null) connection = SqliteConnection(
            "jdbc:sqlite::memory:",
            KotlinLogging.logger("io.tcds.orm")
        )

        return connection!!
    }
}
