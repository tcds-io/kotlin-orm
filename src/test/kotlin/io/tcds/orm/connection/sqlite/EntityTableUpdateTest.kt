package io.tcds.orm.connection.sqlite

import fixtures.Address
import fixtures.AddressEntityTable
import io.tcds.orm.Param
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
                Param(table.id, "galaxy-avenue"),
                Param(table.street, "Galaxy Avenue"),
                Param(table.number, "124T"),
                Param(table.main, true),
                Param(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )
    }

    @Test
    fun `given an entity when update gets called then update in the database`() {
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
            table.loadById("galaxy-avenue")
        )
    }
}
