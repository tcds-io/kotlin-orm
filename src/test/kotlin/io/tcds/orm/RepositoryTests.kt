package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import fixtures.User
import fixtures.UserTable
import io.tcds.orm.driver.SqliteConnection
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class RepositoryTests {
    private val connection = SqliteConnection("jdbc:sqlite::memory:")
    private val addresses = EntityRepository(AddressTable(), connection)
    private val users = EntityRepository(UserTable(addresses), connection)

    init {
        connection.execute("""
            CREATE TABLE addresses
            (
                id         VARCHAR(255) NOT NULL,
                street     VARCHAR(255) NOT NULL,
                number     VARCHAR(255) NOT NULL,
                created_at DATETIME     NOT NULL,
                main       TINYINT      NOT NULL,
                deleted_at DATETIME
            );
        """.trimIndent())

        connection.execute("""
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
    }

    @Test
    fun `when then`() {
        val address = Address(
            id = "arthur-dent-address",
            street = "Galaxy Avenue",
            number = "124T",
            main = true,
            createdAt = LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
            deletedAt = LocalDateTime.of(2022, Month.NOVEMBER, 2, 9, 48, 33),
        )
        val user = User(
            id = "arthur-dent",
            name = "Arthur Dent",
            email = "arthur.dent@galaxy.org",
            height = 1.78,
            age = 42,
            active = true,
            address = address,
        )

        addresses.insert(address)
        users.insert(user)

        val arthur = users.loadById("arthur-dent")

        println(arthur)
    }
}
