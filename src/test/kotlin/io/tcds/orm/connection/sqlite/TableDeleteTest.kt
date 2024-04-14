package io.tcds.orm.connection.sqlite

import fixtures.AddressTable
import io.tcds.orm.param.ColumnParam
import io.tcds.orm.extension.emptyParams
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.toDate
import io.tcds.orm.extension.where
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class TableDeleteTest : SqLiteTestCase() {
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
                ColumnParam(table.id, "another-arthur-dent-address"),
                ColumnParam(table.street, "Galaxy Avenue 2"),
                ColumnParam(table.number, "555T"),
                ColumnParam(table.main, true),
                ColumnParam(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33).toDate()),
            ),
        )
    }

    @Test
    fun `given an entity when delete gets called then the entry gets deleted`() = runBlocking {
        val where = where(table.main equalsTo true)

        table.delete(where)

        Assertions.assertEquals(0, connection().read("SELECT * FROM addresses", emptyParams()).count())
    }
}
