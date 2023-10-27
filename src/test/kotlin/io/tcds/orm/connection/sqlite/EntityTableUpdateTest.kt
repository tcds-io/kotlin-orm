package io.tcds.orm.connection.sqlite

import fixtures.Address
import fixtures.AddressEntityTable
import io.tcds.orm.param.ColumnParam
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class EntityTableUpdateTest : SqLiteTestCase() {
    private val table = AddressEntityTable(connection())

    @BeforeEach
    override fun setup() {
        super.setup()

        connection().write(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                ColumnParam(table.id, "galaxy-avenue"),
                ColumnParam(table.street, "Galaxy Avenue"),
                ColumnParam(table.number, "124T"),
                ColumnParam(table.main, true),
                ColumnParam(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            ),
        )
    }

    @Test
    fun `given an entity when update gets called then update in the database`() = runBlocking {
        val address = Address.galaxyAvenue()
        val updated = address.updated("New Street")

        table.update(updated)

        Assertions.assertEquals(
            Address(
                id = "galaxy-avenue",
                street = "New Street",
                number = "123T",
                main = true,
                createdAt = LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
            ),
            table.loadById("galaxy-avenue"),
        )
    }
}
