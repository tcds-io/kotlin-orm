package io.tcds.orm.connection.sqlite

import fixtures.coClose
import fixtures.coWrite
import io.tcds.orm.connection.GenericConnection
import io.tcds.orm.connection.SqLiteConnection
import mu.KotlinLogging
import org.gradle.internal.impldep.org.jetbrains.annotations.MustBeInvokedByOverriders
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.sql.DriverManager

open class SqLiteTestCase {
    private var connection: SqLiteConnection? = null

    @BeforeEach
    @MustBeInvokedByOverriders
    open fun setup() {
        connection().coWrite(
            """
                CREATE TABLE addresses
                (
                    id         VARCHAR(255) NOT NULL,
                    street     VARCHAR(255) NOT NULL,
                    number     VARCHAR(255) NOT NULL,
                    main       TINYINT      NOT NULL,
                    created_at DATETIME     NOT NULL
                );
            """
        )

        connection().coWrite(
            """
                CREATE TABLE users (
                    id         VARCHAR(255) NOT NULL,
                    name       VARCHAR(255) NOT NULL,
                    email      VARCHAR(255) NOT NULL,
                    height     FLOAT        NOT NULL,
                    age        INT          NOT NULL,
                    active     TINYINT      NOT NULL,
                    address_id VARCHAR(255) NOT NULL
                );
            """
        )

        connection().coWrite(
            """
                CREATE TABLE user_status (
                    user_id    VARCHAR(255) NOT NULL,
                    status     VARCHAR(255) NOT NULL,
                    at         DATETIME     NOT NULL
                );
            """
        )

        connection().coWrite(
            """
                CREATE TABLE user_address (
                    user_id    VARCHAR(255) NOT NULL,
                    address    TEXT NOT NULL
                );
            """
        )
    }

    @AfterEach
    @MustBeInvokedByOverriders
    open fun tearDown() {
        connection().coWrite("DELETE FROM addresses")
        connection().coWrite("DELETE FROM users")
        connection().coClose()
        connection = null
    }

    fun connection(): GenericConnection {
        if (connection === null) connection = SqLiteConnection(
            DriverManager.getConnection("jdbc:sqlite::memory:"),
            KotlinLogging.logger("io.tcds.orm")
        )

        return connection!!
    }
}
