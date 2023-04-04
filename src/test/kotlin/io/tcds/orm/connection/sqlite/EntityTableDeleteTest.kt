package io.tcds.orm.connection.sqlite

import fixtures.Address
import fixtures.AddressEntityTable
import fixtures.coWrite
import io.tcds.orm.Param
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class EntityTableDeleteTest : SqLiteTestCase() {
    private val table = AddressEntityTable(connection())

    @BeforeEach
    override fun setup() {
        super.setup()

        connection().coWrite(
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
    fun `given an entity when delete gets called then the entry gets deleted`() = runBlocking {
        val address = Address.galaxyAvenue()

        table.delete(address)

        Assertions.assertEquals(
            0,
            connection()
                .read("SELECT * FROM addresses WHERE id = ?", listOf(Param(table.id, "galaxy-avenue")))
                .count()
        )
    }
}
