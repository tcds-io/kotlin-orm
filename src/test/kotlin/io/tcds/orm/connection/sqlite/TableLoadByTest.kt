package io.tcds.orm.connection.sqlite

import fixtures.AddressTable
import io.tcds.orm.param.ColumnParam
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.toDate
import io.tcds.orm.extension.where
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class TableLoadByTest : SqLiteTestCase() {
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
    }

    @Test
    fun `given a condition and ASC order when entry exists then load into the entity`() = runBlocking {
        val where = where(table.main equalsTo true)
        val order = listOf(table.id.asc())

        val address = table.loadBy(where, order = order)

        Assertions.assertEquals("arthur-dent-address", address?.id)
    }

    @Test
    fun `given a condition and DESC order when entry exists then load into the entity`() = runBlocking {
        val where = where(table.main equalsTo true)

        val address = table.loadBy(where, order = listOf(table.id.desc()))

        Assertions.assertEquals("arthur-dent-address-another-address", address?.id)
    }
}
