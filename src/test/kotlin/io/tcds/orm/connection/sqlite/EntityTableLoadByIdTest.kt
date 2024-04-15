package io.tcds.orm.connection.sqlite

import fixtures.Address
import fixtures.AddressEntityTable
import fixtures.frozenClockAtApril
import io.tcds.orm.param.ColumnParam
import kotlinx.coroutines.runBlocking
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
                ColumnParam(table.id, "arthur-dent-address"),
                ColumnParam(table.street, "Galaxy Avenue"),
                ColumnParam(table.number, "124T"),
                ColumnParam(table.main, true),
                ColumnParam(table.createdAt, frozenClockAtApril),
            ),
        )
    }

    @Test
    fun `given and user id when user exists then loadById returns an user entity`() = runBlocking {
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
    fun `given and user id when user does not exist then loadById returns null`() = runBlocking {
        val id = "another-user"

        val address = table.loadById(id)

        Assertions.assertNull(address)
    }
}
