package io.tcds.orm.connection.sqlite

import fixtures.Address
import fixtures.AddressEntityTable
import io.tcds.orm.extension.toDate
import io.tcds.orm.param.ColumnParam
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

        connection().write(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                ColumnParam(table.id, "galaxy-avenue"),
                ColumnParam(table.street, "Galaxy Avenue"),
                ColumnParam(table.number, "124T"),
                ColumnParam(table.main, true),
                ColumnParam(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33).toDate()),
            ),
        )
    }

    @Test
    fun `given an entity when delete gets called then the entry gets deleted`() = runBlocking {
        val address = Address.galaxyAvenue()

        table.delete(address)

        Assertions.assertEquals(
            0,
            connection()
                .read("SELECT * FROM addresses WHERE id = ?", listOf(ColumnParam(table.id, "galaxy-avenue")))
                .count(),
        )
    }
}
