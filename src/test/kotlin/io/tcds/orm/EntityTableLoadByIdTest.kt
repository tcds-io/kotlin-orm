package io.tcds.orm

import fixtures.Address
import fixtures.AddressEntityTable
import fixtures.MapOrmResultSet
import io.mockk.*
import io.tcds.orm.connection.Connection
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

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
        coEvery { connection.read(any(), any()) } returns sequenceOf(
            MapOrmResultSet(
                mapOf(
                    table.id to "galaxy-highway",
                    table.street to "Galaxy Highway",
                    table.number to "678H",
                    table.main to false,
                    table.createdAt to LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
                ),
            )
        )

        val result = runBlocking { table.loadById("galaxy-highway") }

        Assertions.assertEquals(address, result)
        coVerify { connection.read(EXPECTED_QUERY, listOf(Param(table.id, "galaxy-highway"))) }
    }

    @Test
    fun `given a where when table is soft delete and order then invoke read from connection`() {
        val table = AddressEntityTable(connection, true)
        coEvery { connection.read(any(), any()) } returns sequenceOf(
            MapOrmResultSet(
                mapOf(
                    table.id to "galaxy-highway",
                    table.street to "Galaxy Highway",
                    table.number to "678H",
                    table.main to false,
                    table.createdAt to LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
                ),
            )
        )

        val result = runBlocking { table.loadById("galaxy-highway") }

        Assertions.assertEquals(address, result)
        coVerify { connection.read(EXPECTED_SOFT_DELETE_QUERY, listOf(Param(table.id, "galaxy-highway"))) }
    }
}
