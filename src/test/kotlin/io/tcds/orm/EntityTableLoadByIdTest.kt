package io.tcds.orm

import fixtures.Address
import fixtures.AddressEntityTable
import fixtures.frozenClockAtApril
import io.mockk.*
import io.tcds.orm.connection.Connection
import io.tcds.orm.param.StringParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EntityTableLoadByIdTest {
    companion object {
        private val EXPECTED_QUERY = """
            SELECT * FROM addresses WHERE id = ? LIMIT 1
        """.trimIndent()

        private val EXPECTED_SOFT_DELETE_QUERY = """
            SELECT * FROM addresses WHERE (id = ?) AND deleted_at IS NULL LIMIT 1
        """.trimIndent()
    }

    private val connection: Connection = mockk()
    private val address = Address.galaxyHighway()

    @Test
    fun `given a where and order then invoke read from connection`() {
        val table = AddressEntityTable(connection)
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

        val result = table.loadById("galaxy-highway")

        Assertions.assertEquals(address, result)
        verify { connection.read(EXPECTED_QUERY, listOf(StringParam("id", "galaxy-highway"))) }
    }

    @Test
    fun `given a where when table is soft delete and order then invoke read from connection`() {
        val table = AddressEntityTable(connection, true)
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

        val result = table.loadById("galaxy-highway")

        Assertions.assertEquals(address, result)
        verify { connection.read(EXPECTED_SOFT_DELETE_QUERY, listOf(StringParam("id", "galaxy-highway"))) }
    }
}
