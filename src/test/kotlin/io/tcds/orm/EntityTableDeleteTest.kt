package io.tcds.orm

import fixtures.Address
import fixtures.AddressEntityTable
import fixtures.freezeClock
import fixtures.frozenClockAt
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.tcds.orm.connection.Connection
import io.tcds.orm.statement.Statement
import org.junit.jupiter.api.Test

class EntityTableDeleteTest {
    private val connection: Connection = mockk()
    private val address = Address.galaxyAvenue()

    @Test
    fun `given the entry when table is not soft delete then invoke delete in the write connection`() {
        val table = AddressEntityTable(connection)
        every { connection.write(any(), any()) } returns true

        table.delete(address)

        verify { connection.write("DELETE FROM addresses WHERE id = ?", listOf(Param(table.id, "galaxy-avenue"))) }
    }

    @Test
    fun `given the entry when table is soft delete then invoke update in the write connection`() {
        val table = AddressEntityTable(connection, true)
        every { connection.write(any(), any()) } returns true

        freezeClock { table.delete(address) }

        verify {
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
