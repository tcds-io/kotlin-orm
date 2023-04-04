package io.tcds.orm.connection.sqlite

import fixtures.Address
import fixtures.AddressTable
import fixtures.coWrite
import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import io.tcds.orm.statement.Order
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

        connection().coWrite(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                Param(table.id, "arthur-dent-address"),
                Param(table.street, "Galaxy Avenue"),
                Param(table.number, "124T"),
                Param(table.main, true),
                Param(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )

        connection().coWrite(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                Param(table.id, "arthur-dent-address-another-address"),
                Param(table.street, "Galaxy Avenue"),
                Param(table.number, "124T"),
                Param(table.main, true),
                Param(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )
    }

    @Test
    fun `given a condition and ASC order when entry exists then load into the entity`() = runBlocking {
        val where = where(table.main equalsTo true)
        val order = mapOf<Column<Address, *>, Order>(table.id to Order.ASC)

        val address = table.loadBy(where, order = order)

        Assertions.assertEquals("arthur-dent-address", address?.id)
    }

    @Test
    fun `given a condition and DESC order when entry exists then load into the entity`() = runBlocking {
        val where = where(table.main equalsTo true)

        val address = table.loadBy(where, order = mapOf(table.id to Order.DESC))

        Assertions.assertEquals("arthur-dent-address-another-address", address?.id)
    }
}
