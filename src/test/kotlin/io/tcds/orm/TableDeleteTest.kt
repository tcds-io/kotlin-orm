package io.tcds.orm

import fixtures.AddressTable
import fixtures.freezeClock
import fixtures.frozenClockAt
import io.mockk.*
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import io.tcds.orm.param.InstantParam
import io.tcds.orm.param.StringParam
import org.junit.jupiter.api.Test

class TableDeleteTest {
    private val connection: Connection = mockk()

    @Test
    fun `given the entry when table is not soft delete then invoke delete in the write connection`() {
        val table = AddressTable(connection)
        every { connection.write(any(), any()) } returns mockk()

        table.delete(where(table.id equalsTo "galaxy-avenue"))

        verify {
            connection.write(
                "DELETE FROM addresses WHERE id = ?",
                listOf(StringParam("id", "galaxy-avenue")),
            )
        }
    }

    @Test
    fun `given the entry when table is soft delete then invoke update in the write connection`() = freezeClock {
        val table = AddressTable(connection, true)
        every { connection.write(any(), any()) } returns mockk()

        table.delete(where(table.id equalsTo "galaxy-avenue"))

        verify {
            connection.write(
                "UPDATE addresses SET deleted_at = ? WHERE id = ?",
                listOf(
                    InstantParam("deleted_at", frozenClockAt),
                    StringParam("id", "galaxy-avenue"),
                ),
            )
        }
    }
}
