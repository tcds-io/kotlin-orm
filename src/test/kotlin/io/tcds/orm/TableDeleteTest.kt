package io.tcds.orm

import fixtures.AddressTable
import fixtures.freezeClock
import fixtures.frozenClockAt
import io.mockk.*
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import io.tcds.orm.statement.Statement
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class TableDeleteTest {
    private val connection: Connection = mockk()

    @Test
    fun `given the entry when table is not soft delete then invoke delete in the write connection`() {
        val table = AddressTable(connection)
        coEvery { connection.write(any(), any()) } returns true

        runBlocking { table.delete(where(table.id equalsTo "galaxy-avenue")) }

        coVerify { connection.write("DELETE FROM addresses WHERE id = ?", listOf(Param(table.id, "galaxy-avenue"))) }
    }

    @Test
    fun `given the entry when table is soft delete then invoke update in the write connection`() =freezeClock {
        val table = AddressTable(connection, true)
        coEvery { connection.write(any(), any()) } returns true

        runBlocking { table.delete(where(table.id equalsTo "galaxy-avenue")) }

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
