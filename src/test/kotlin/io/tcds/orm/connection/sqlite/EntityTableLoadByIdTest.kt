package io.tcds.orm.connection.sqlite

import fixtures.Address
import fixtures.AddressEntityTable
import io.tcds.orm.Param
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class EntityTableLoadByIdTest : SqLiteTestCase() {
    private val table = AddressEntityTable(connection())

    @BeforeEach
    override fun setup() {
        super.setup()

        connection().write(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                Param(table.id, "arthur-dent-address"),
                Param(table.street, "Galaxy Avenue"),
                Param(table.number, "124T"),
                Param(table.main, true),
                Param(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )
    }

    @Test
    fun `given and user id when user exists then loadById returns an user entity`() {
        val id = "arthur-dent-address"

        val address = table.loadById(id)

        Assertions.assertEquals(
            Address(
                id = "arthur-dent-address",
                street = "Galaxy Avenue",
                number = "124T",
                main = true,
                createdAt = LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
            ),
            address,
        )
    }

    @Test
    fun `given and user id when user does not exist then loadById returns null`() {
        val id = "another-user"

        val address = table.loadById(id)

        Assertions.assertNull(address)
    }
}
