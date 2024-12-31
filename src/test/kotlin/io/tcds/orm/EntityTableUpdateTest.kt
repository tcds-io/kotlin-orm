package io.tcds.orm

import fixtures.Address
import fixtures.AddressEntityTable
import io.mockk.*
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.toInstant
import io.tcds.orm.extension.trimSpacesAndLines
import io.tcds.orm.param.BooleanParam
import io.tcds.orm.param.InstantParam
import io.tcds.orm.param.StringParam
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
        every { connection.write(any(), any()) } returns mockk()

        val updated = address.updated(street = "new street", number = "new number", main = false)
        table.update(updated)

        verify {
            connection.write(
                EXPECTED_QUERY,
                listOf(
                    StringParam(table.street.name, "new street"),
                    StringParam(table.number.name, "new number"),
                    BooleanParam(table.main.name, false),
                    InstantParam(table.createdAt.name, address.createdAt.toInstant()),
                    StringParam(table.id.name, "galaxy-avenue"),
                ),
            )
        }
    }

    @Test
    fun `given the params and where condition when table is soft delete then run update`() {
        val table = AddressEntityTable(connection, true)
        every { connection.write(any(), any()) } returns mockk()

        val updated = address.updated(street = "new street", number = "new number", main = false)
        table.update(updated)

        verify {
            connection.write(
                EXPECTED_SOFT_DELETE_QUERY,
                listOf(
                    StringParam(table.street.name, "new street"),
                    StringParam(table.number.name, "new number"),
                    BooleanParam(table.main.name, false),
                    InstantParam(table.createdAt.name, address.createdAt.toInstant()),
                    StringParam(table.id.name, "galaxy-avenue"),
                ),
            )
        }
    }
}
