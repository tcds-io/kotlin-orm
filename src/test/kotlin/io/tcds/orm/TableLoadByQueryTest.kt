package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import fixtures.frozenClockAtApril
import io.mockk.*
import io.tcds.orm.connection.Connection
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TableLoadByQueryTest {
    companion object {
        private const val QUERY = "SELECT * FROM addresses WHERE main = ?"
    }

    private val connection: Connection = mockk()
    private val table = AddressTable(connection)

    private val address = Address.galaxyHighway()

    @Test
    fun `given the query then invoke write in the connection`() {
        every { connection.read(any(), any()) } returns sequenceOf(
            MapOrmResultSet(
                mapOf(
                    table.id.name to "galaxy-highway",
                    table.street.name to "Galaxy Highway",
                    table.number.name to "678H",
                    table.main.name to false,
                    table.createdAt.name to frozenClockAtApril,
                ),
            ),
        )

        val result = table.loadByQuery(QUERY, ColumnParam(table.main, true))

        Assertions.assertEquals(address, result)
        verify { connection.read(QUERY, listOf(ColumnParam(table.main, true))) }
    }
}
