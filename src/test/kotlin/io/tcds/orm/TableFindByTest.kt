package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import fixtures.MapOrmResultSet
import io.mockk.every
import io.mockk.verify
import io.mockk.mockk
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.like
import io.tcds.orm.extension.where
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

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

        val result = runBlocking {
            table.findBy(
                where = where(table.street like "Galaxy%"),
                order = listOf(table.createdAt.desc()),
                limit = 15,
                offset = 30,
            ).toList()
        }

        Assertions.assertEquals(listOf(Address.galaxyHighway(), Address.galaxyAvenue()), result)
        verify { connection.read(EXPECTED_QUERY, listOf(Param(table.street, "Galaxy%"))) }
    }
}
