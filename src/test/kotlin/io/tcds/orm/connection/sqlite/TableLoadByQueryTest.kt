package io.tcds.orm.connection.sqlite

import fixtures.AddressTable
import fixtures.frozenClockAtApril
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TableLoadByQueryTest : SqLiteTestCase() {
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
                ColumnParam(table.id, "something-else"),
                ColumnParam(table.street, "Galaxy Avenue"),
                ColumnParam(table.number, "789A"),
                ColumnParam(table.main, true),
                ColumnParam(table.createdAt, frozenClockAtApril),
            ),
        )
    }

    @Test
    fun `given a sql query and ASC order when entry exists then load into the entity`() {
        val sql = "SELECT * FROM addresses ORDER BY id ASC LIMIT 1"

        val address = table.loadByQuery(sql)

        Assertions.assertEquals("arthur-dent-address", address?.id)
    }

    @Test
    fun `given a sql and a condition when entry exists then load into the entity`() {
        val sql = "SELECT * FROM addresses ORDER BY id DESC LIMIT 1"

        val address = table.loadByQuery(sql)

        Assertions.assertEquals("something-else", address?.id)
    }

    @Test
    fun `given a sql query and DESC order when entry exists then load into the entity`() {
        val sql = "SELECT * FROM addresses WHERE number = ? ORDER BY id DESC LIMIT 1"
        val param = ColumnParam(table.number, "124T")

        val address = table.loadByQuery(sql, param)

        Assertions.assertEquals("arthur-dent-address-another-address", address?.id)
    }
}
