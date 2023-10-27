package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import io.tcds.orm.param.ColumnParam
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class TableLoadByTest {
    companion object {
        private val EXPECTED_QUERY = """
            SELECT * FROM addresses WHERE street = ? ORDER BY created_at DESC LIMIT 1
        """.trimIndent()

        private val EXPECTED_SOFT_DELETE_QUERY = """
            SELECT * FROM addresses WHERE (street = ?) AND deleted_at IS NULL ORDER BY created_at DESC LIMIT 1
        """.trimIndent()
    }

    private val connection: Connection = mockk()

    private val address = Address.galaxyHighway()

    @Test
    fun `given a where and order then invoke read from connection`() {
        val table = AddressTable(connection)
        every { connection.read(any(), any()) } returns sequenceOf(
            MapOrmResultSet(
                mapOf(
                    table.id.name to "galaxy-highway",
                    table.street.name to "Galaxy Highway",
                    table.number.name to "678H",
                    table.main.name to false,
                    table.createdAt.name to LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
                ),
            ),
        )

        val result = runBlocking {
            table.loadBy(
                where(table.street equalsTo "Galaxy Highway"),
                listOf(table.createdAt.desc()),
            )
        }

        Assertions.assertEquals(address, result)
        verify { connection.read(EXPECTED_QUERY, listOf(ColumnParam(table.street, "Galaxy Highway"))) }
    }

    @Test
    fun `given a where when table is soft delete and order then invoke read from connection`() {
        val table = AddressTable(connection, true)
        every { connection.read(any(), any()) } returns sequenceOf(
            MapOrmResultSet(
                mapOf(
                    table.id.name to "galaxy-highway",
                    table.street.name to "Galaxy Highway",
                    table.number.name to "678H",
                    table.main.name to false,
                    table.createdAt.name to LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
                ),
            ),
        )

        val result = runBlocking {
            table.loadBy(
                where(table.street equalsTo "Galaxy Highway"),
                listOf(table.createdAt.desc()),
            )
        }

        Assertions.assertEquals(address, result)
        verify { connection.read(EXPECTED_SOFT_DELETE_QUERY, listOf(ColumnParam(table.street, "Galaxy Highway"))) }
    }
}
