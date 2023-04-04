package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import fixtures.MapOrmResultSet
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.tcds.orm.connection.Connection
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class TableFindByQueryTest {
    companion object {
        private val QUERY = """
            SELECT * FROM addresses
                WHERE street LIKE ?
                ORDER BY created_at DESC
                LIMIT 50
        """.trimIndent()
    }

    private val connection: Connection = mockk()
    private val table = AddressTable(connection)

    @Test
    fun `given a query then invoke read from connection`() {
        coEvery { connection.read(any(), any()) } returns sequenceOf(
            MapOrmResultSet(
                mapOf(
                    table.id to "galaxy-highway",
                    table.street to "Galaxy Highway",
                    table.number to "678H",
                    table.main to false,
                    table.createdAt to LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
                ),
            ),
            MapOrmResultSet(
                mapOf(
                    table.id to "galaxy-avenue",
                    table.street to "Galaxy Avenue",
                    table.number to "123T",
                    table.main to true,
                    table.createdAt to LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
                ),
            ),
        )

        val result = runBlocking { table.findByQuery(QUERY, listOf(Param(table.street, "Galaxy%"))).toList() }

        Assertions.assertEquals(listOf(Address.galaxyHighway(), Address.galaxyAvenue()), result)
        coVerify { connection.read(QUERY, listOf(Param(table.street, "Galaxy%"))) }
    }
}
