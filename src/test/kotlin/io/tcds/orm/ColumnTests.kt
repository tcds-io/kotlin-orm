package io.tcds.orm

import fixtures.AddressTable
import fixtures.driver.DummyConnection
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ColumnTests {
    private val table = AddressTable(DummyConnection.dummy())

    @Test
    fun `given a statement then compare with other boolean statement`() {
        val statement = where(table.main equalsTo true)

        Assertions.assertEquals(where(table.main equalsTo true), statement)
    }

    @Test
    fun `given a statement then compare with other string statement`() {
        val statement = where(table.street equalsTo "Av. Paulista")

        Assertions.assertEquals(where(table.street equalsTo "Av. Paulista"), statement)
    }
}
