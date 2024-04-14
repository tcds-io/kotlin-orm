package io.tcds.orm.connection.sqlite

import fixtures.AddressTable
import io.tcds.orm.extension.toDate
import io.tcds.orm.param.ColumnParam
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class TableFindByQueryTest : SqLiteTestCase() {
    private val table = AddressTable(connection())

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
                ColumnParam(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33).toDate()),
            ),
        )

        connection().write(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                ColumnParam(table.id, "arthur-dent-address-another-address"),
                ColumnParam(table.street, "Galaxy Avenue"),
                ColumnParam(table.number, "124T"),
                ColumnParam(table.main, true),
                ColumnParam(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33).toDate()),
            ),
        )

        connection().write(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                ColumnParam(table.id, "another-address"),
                ColumnParam(table.street, "Galaxy Avenue"),
                ColumnParam(table.number, "124T"),
                ColumnParam(table.main, true),
                ColumnParam(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33).toDate()),
            ),
        )
    }

    @Test
    fun `given a condition and ASC order and limit and offset when entries exist then select into the database`() {
        val sql = "SELECT * FROM addresses ORDER BY id ASC"

        val addresses = runBlocking { table.findByQuery(sql) }

        Assertions.assertEquals(
            listOf("another-address", "arthur-dent-address", "arthur-dent-address-another-address"),
            addresses.map { it.id }.toList(),
        )
    }
}
