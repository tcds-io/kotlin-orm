package io.tcds.orm

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

class TableExistsTest {
    companion object {
        private const val EXPECTED_QUERY = "SELECT * FROM addresses WHERE street LIKE ? LIMIT 1"
    }

    private val connection: Connection = mockk()
    private val table = AddressTable(connection)

    @Test
    fun `given a where when connection returns entries then exist is true`() {
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
        )

        val result = runBlocking { table.exists(where = where(table.street like "Galaxy%")) }

        Assertions.assertTrue(result)
        verify { connection.read(EXPECTED_QUERY, listOf(Param(table.street, "Galaxy%"))) }
    }

    @Test
    fun `given a where when connection does not return entries then exist is false`() {
        every { connection.read(any(), any()) } returns emptySequence()

        val result = runBlocking { table.exists(where = where(table.street like "Galaxy%")) }

        Assertions.assertFalse(result)
        verify { connection.read(EXPECTED_QUERY, listOf(Param(table.street, "Galaxy%"))) }
    }
}
