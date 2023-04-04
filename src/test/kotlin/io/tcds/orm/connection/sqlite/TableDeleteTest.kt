package io.tcds.orm.connection.sqlite

import fixtures.AddressTable
import io.tcds.orm.Param
import io.tcds.orm.extension.emptyParams
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
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
                Param(table.id, "arthur-dent-address"),
                Param(table.street, "Galaxy Avenue"),
                Param(table.number, "124T"),
                Param(table.main, true),
                Param(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )

        connection().write(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                Param(table.id, "another-arthur-dent-address"),
                Param(table.street, "Galaxy Avenue 2"),
                Param(table.number, "555T"),
                Param(table.main, true),
                Param(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )
    }

    @Test
    fun `given an entity when delete gets called then the entry gets deleted`() {
        val where = where(table.main equalsTo true)

        table.delete(where)

        Assertions.assertEquals(0, connection().read("SELECT * FROM addresses", emptyParams()).count())
    }
}
