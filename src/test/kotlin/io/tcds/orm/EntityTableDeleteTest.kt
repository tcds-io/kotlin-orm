package io.tcds.orm

import fixtures.Address
import fixtures.AddressEntityTable
import fixtures.freezeClock
import fixtures.frozenClockAt
import io.mockk.*
import io.tcds.orm.connection.Connection
import io.tcds.orm.param.InstantParam
import io.tcds.orm.param.StringParam
import org.junit.jupiter.api.Test

class EntityTableDeleteTest {
    private val connection: Connection = mockk()
    private val address = Address.galaxyAvenue()

    @Test
    fun `given the entry when table is not soft delete then invoke delete in the write connection`() {
        val table = AddressEntityTable(connection)
        every { connection.write(any(), any()) } returns mockk()

        table.delete(address)

        verify { connection.write("DELETE FROM addresses WHERE id = ?", listOf(StringParam(table.id.name, "galaxy-avenue"))) }
    }

    @Test
    fun `given the entry when table is soft delete then invoke update in the write connection`() = freezeClock {
        val table = AddressEntityTable(connection, true)
        every { connection.write(any(), any()) } returns mockk()

        table.delete(address)

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
