package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import fixtures.frozenClockAtApril
import io.mockk.*
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.like
import io.tcds.orm.extension.where
import io.tcds.orm.param.StringParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TableFindByTest {
    companion object {
        private val EXPECTED_QUERY = """
            SELECT * FROM addresses WHERE street LIKE ? ORDER BY created_at DESC LIMIT 15 OFFSET 30
        """.trimIndent()
    }

    private val connection: Connection = mockk()
    private val table = AddressTable(connection)

    @Test
    fun `given a where and order and limit and offset then invoke read from connection`() {
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
            MapOrmResultSet(
                mapOf(
                    table.id.name to "galaxy-avenue",
                    table.street.name to "Galaxy Avenue",
                    table.number.name to "123T",
                    table.main.name to true,
                    table.createdAt.name to frozenClockAtApril,
                ),
            ),
        )

        val result = table.findBy(
            where = where(table.street like "Galaxy%"),
            order = listOf(table.createdAt.desc()),
            limit = 15,
            offset = 30,
        ).toList()

        Assertions.assertEquals(listOf(Address.galaxyHighway(), Address.galaxyAvenue()), result)
        verify { connection.read(EXPECTED_QUERY, listOf(StringParam(table.street.name, "Galaxy%"))) }
    }
}
