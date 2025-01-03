package io.tcds.orm.connection.sqlite

import fixtures.AddressTable
import fixtures.frozenClockAtApril
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TableFindByTest : SqLiteTestCase() {
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
                ColumnParam(table.createdAt, frozenClockAtApril),
            ),
        )

        connection().write(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                ColumnParam(table.id, "arthur-dent-address-another-address"),
                ColumnParam(table.street, "Galaxy Avenue"),
                ColumnParam(table.number, "124T"),
                ColumnParam(table.main, true),
                ColumnParam(table.createdAt, frozenClockAtApril),
            ),
        )

        connection().write(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                ColumnParam(table.id, "another-address"),
                ColumnParam(table.street, "Galaxy Avenue"),
                ColumnParam(table.number, "124T"),
                ColumnParam(table.main, true),
                ColumnParam(table.createdAt, frozenClockAtApril),
            ),
        )
    }

    @Test
    fun `given a condition and ASC order when entries exist then select into the database`() {
        val where = where(table.main equalsTo true)
        val order = listOf(table.id.asc())
        val limit = 2

        val addresses = table.findBy(where, order, limit)

        Assertions.assertEquals(
            listOf("another-address", "arthur-dent-address"),
            addresses.map { it.id }.toList(),
        )
    }

    @Test
    fun `given a condition and ASC order and limit and offset when entries exist then select into the database`() {
        val where = where(table.main equalsTo true)
        val order = listOf(table.id.asc())
        val limit = 2
        val offset = 1

        val addresses = table.findBy(where, order, limit, offset)

        Assertions.assertEquals(
            listOf("arthur-dent-address", "arthur-dent-address-another-address"),
            addresses.map { it.id }.toList(),
        )
    }
}
