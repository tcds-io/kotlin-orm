package io.tcds.orm

import fixtures.AddressTable
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ColumnTests {
    private val table = AddressTable()

    @Test
    fun `given a statement then compare with other statement`() {
        val statement = where(table.main equalsTo true)

        Assertions.assertEquals(where(table.main equalsTo true), statement)
    }
}
