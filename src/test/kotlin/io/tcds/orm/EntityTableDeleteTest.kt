package io.tcds.orm

import fixtures.Address
import fixtures.AddressEntityTable
import fixtures.freezeClock
import fixtures.frozenClockAt
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.tcds.orm.connection.Connection
import io.tcds.orm.statement.Statement
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class EntityTableDeleteTest {
    private val connection: Connection = mockk()
    private val address = Address.galaxyAvenue()

    @Test
    fun `given the entry when table is not soft delete then invoke delete in the write connection`() {
        val table = AddressEntityTable(connection)
        coEvery { connection.write(any(), any()) } returns true

        runBlocking { table.delete(address) }

        coVerify { connection.write("DELETE FROM addresses WHERE id = ?", listOf(Param(table.id, "galaxy-avenue"))) }
    }

    @Test
    fun `given the entry when table is soft delete then invoke update in the write connection`() = freezeClock {
        val table = AddressEntityTable(connection, true)
        coEvery { connection.write(any(), any()) } returns true

        runBlocking { table.delete(address) }

        coVerify {
            connection.write(
                "UPDATE addresses SET deleted_at = ? WHERE id = ?",
                listOf(
                    Param(Statement.deletedAt(), frozenClockAt),
                    Param(table.id, "galaxy-avenue"),
                ),
            )
        }
    }
}
