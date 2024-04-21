package io.tcds.orm.extension

import fixtures.AddressTable
import fixtures.connection.DummyConnection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OrderTest {
    private val table = AddressTable(DummyConnection.dummy())

    @Test
    fun `given a single column then create order statement`() {
        val order = orderBy(table.street.asc())

        Assertions.assertEquals("ORDER BY street ASC", order.toOrderByStatement())
    }

    @Test
    fun `given a multiple columns then create order statement`() {
        val order = orderBy(table.street.desc())
            .orderBy(table.number.asc())
            .orderBy(table.createdAt.desc())

        Assertions.assertEquals("ORDER BY street DESC, number ASC, created_at DESC", order.toOrderByStatement())
    }
}
