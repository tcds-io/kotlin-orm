package io.tcds.orm.connection.sqlite

import fixtures.AddressTable
import fixtures.frozenClockAtApril
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TableExistsTest : SqLiteTestCase() {
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
    }

    @Test
    fun `given a condition when entry exists then exists returns true`() {
        val where = where(table.main equalsTo true)

        val exists = table.exists(where)

        Assertions.assertTrue(exists)
    }

    @Test
    fun `given a condition when entry does not exist then exists returns false`() {
        val where = where(table.main equalsTo false)

        val exists = table.exists(where)

        Assertions.assertFalse(exists)
    }
}
