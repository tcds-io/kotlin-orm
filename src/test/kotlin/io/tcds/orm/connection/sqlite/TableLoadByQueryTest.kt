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
                ColumnParam(table.id, "something-else"),
                ColumnParam(table.street, "Galaxy Avenue"),
                ColumnParam(table.number, "789A"),
                ColumnParam(table.main, true),
                ColumnParam(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33).toDate()),
            ),
        )
    }

    @Test
    fun `given a sql query and ASC order when entry exists then load into the entity`() = runBlocking {
        val sql = "SELECT * FROM addresses ORDER BY id ASC LIMIT 1"

        val address = table.loadByQuery(sql)

        Assertions.assertEquals("arthur-dent-address", address?.id)
    }

    @Test
    fun `given a sql and a condition when entry exists then load into the entity`() = runBlocking {
        val sql = "SELECT * FROM addresses ORDER BY id DESC LIMIT 1"

        val address = table.loadByQuery(sql)

        Assertions.assertEquals("something-else", address?.id)
    }

    @Test
    fun `given a sql query and DESC order when entry exists then load into the entity`() = runBlocking {
        val sql = "SELECT * FROM addresses WHERE number = ? ORDER BY id DESC LIMIT 1"
        val params = listOf(ColumnParam(table.number, "124T"))

        val address = table.loadByQuery(sql, params)

        Assertions.assertEquals("arthur-dent-address-another-address", address?.id)
    }
}
