package io.tcds.orm

import fixtures.AddressTable
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.and
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.isNotNull
import io.tcds.orm.extension.where
import io.tcds.orm.param.ColumnParam
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class TableUpdateTest {
    private val connection: Connection = mockk()

    @Test
    fun `given the params and where condition when table is not soft delete then run update`() {
        val table = AddressTable(connection)
        every { connection.write(any(), any()) } returns mockk()

        runBlocking {
            table.update(
                listOf(ColumnParam(table.street, "Galaxy Highway")),
                where(table.id equalsTo "galaxy-avenue"),
            )
        }

        verify {
            connection.write(
                "UPDATE addresses SET street = ? WHERE id = ?",
                listOf(ColumnParam(table.street, "Galaxy Highway"), ColumnParam(table.id, "galaxy-avenue")),
            )
        }
    }

    @Test
    fun `given the params and where condition when table is soft delete then run update`() {
        val table = AddressTable(connection, true)
        every { connection.write(any(), any()) } returns mockk()

        runBlocking {
            table.update(
                listOf(ColumnParam(table.street, "Galaxy Highway")),
                where(table.id equalsTo "galaxy-avenue") and table.street.isNotNull(),
            )
        }

        verify {
            connection.write(
                "UPDATE addresses SET street = ? WHERE (id = ? AND street IS NOT NULL) AND deleted_at IS NULL",
                listOf(ColumnParam(table.street, "Galaxy Highway"), ColumnParam(table.id, "galaxy-avenue")),
            )
        }
    }
}
