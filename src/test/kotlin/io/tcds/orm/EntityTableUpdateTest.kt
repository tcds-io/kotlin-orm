package io.tcds.orm

import fixtures.Address
import fixtures.AddressEntityTable
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.trimSpacesAndLines
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class EntityTableUpdateTest {
    companion object {
        private val EXPECTED_QUERY = """
            UPDATE addresses SET street = ?, number = ?, main = ?, created_at = ?
                WHERE id = ?
        """.trimSpacesAndLines()

        private val EXPECTED_SOFT_DELETE_QUERY = """
            UPDATE addresses SET street = ?, number = ?, main = ?, created_at = ?
                WHERE (id = ?) AND deleted_at IS NULL
        """.trimSpacesAndLines()
    }

    private val connection: Connection = mockk()
    private val address = Address.galaxyAvenue()

    @Test
    fun `given the params and where condition when table is not soft delete then run update`() {
        val table = AddressEntityTable(connection)
        every { connection.write(any(), any()) } returns true

        val updated = address.updated(street = "new street", number = "new number", main = false)
        runBlocking { table.update(updated) }

        verify {
            connection.write(
                EXPECTED_QUERY,
                listOf(
                    Param(table.street, "new street"),
                    Param(table.number, "new number"),
                    Param(table.main, false),
                    Param(table.createdAt, address.createdAt),
                    Param(table.id, "galaxy-avenue"),
                ),
            )
        }
    }

    @Test
    fun `given the params and where condition when table is soft delete then run update`() {
        val table = AddressEntityTable(connection, true)
        every { connection.write(any(), any()) } returns true

        val updated = address.updated(street = "new street", number = "new number", main = false)
        runBlocking { table.update(updated) }

        verify {
            connection.write(
                EXPECTED_SOFT_DELETE_QUERY,
                listOf(
                    Param(table.street, "new street"),
                    Param(table.number, "new number"),
                    Param(table.main, false),
                    Param(table.createdAt, address.createdAt),
                    Param(table.id, "galaxy-avenue"),
                ),
            )
        }
    }
}
