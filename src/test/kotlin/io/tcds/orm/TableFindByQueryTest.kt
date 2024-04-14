package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.toDate
import io.tcds.orm.param.ColumnParam
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
        every { connection.read(any(), any()) } returns sequenceOf(
            MapOrmResultSet(
                mapOf(
                    table.id.name to "galaxy-highway",
                    table.street.name to "Galaxy Highway",
                    table.number.name to "678H",
                    table.main.name to false,
                    table.createdAt.name to LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33).toDate(),
                ),
            ),
            MapOrmResultSet(
                mapOf(
                    table.id.name to "galaxy-avenue",
                    table.street.name to "Galaxy Avenue",
                    table.number.name to "123T",
                    table.main.name to true,
                    table.createdAt.name to LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33).toDate(),
                ),
            ),
        )

        val result = runBlocking { table.findByQuery(QUERY, listOf(ColumnParam(table.street, "Galaxy%"))).toList() }

        Assertions.assertEquals(listOf(Address.galaxyHighway(), Address.galaxyAvenue()), result)
        verify { connection.read(QUERY, listOf(ColumnParam(table.street, "Galaxy%"))) }
    }
}
