package io.tcds.orm.connection.sqlite

import fixtures.Address
import fixtures.AddressTable
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TableInsertTest : SqLiteTestCase() {
    private val table = AddressTable(connection())

    @Test
    fun `given an entity when insert gets called then the entry gets inserted`() {
        val address = Address.galaxyHighway()

        table.insert(address)

        Assertions.assertEquals(
            listOf(address),
            connection().read(
                "SELECT * FROM addresses WHERE id = ?",
                listOf(ColumnParam(table.id, "galaxy-highway")),
            ).map { table.entry(it) }.toList(),
        )
    }
}
