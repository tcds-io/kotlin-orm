package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import fixtures.MapOrmResultSet
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.tcds.orm.connection.Connection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

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
                    table.id to "galaxy-highway",
                    table.street to "Galaxy Highway",
                    table.number to "678H",
                    table.main to false,
                    table.createdAt to LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
                ),
            )
        )

        val result = table.loadByQuery(QUERY, listOf(Param(table.main, true)))

        Assertions.assertEquals(address, result)
        verify { connection.read(QUERY, listOf(Param(table.main, true))) }
    }
}
