package io.tcds.orm.driver

import fixtures.AddressTable
import fixtures.SoftDeleteAddressTable
import fixtures.driver.DummyConnection
import fixtures.freezeClockAt
import io.mockk.*
import io.tcds.orm.Param
import io.tcds.orm.extension.differentOf
import io.tcds.orm.extension.emptyWhere
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import io.tcds.orm.statement.Order
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.sql.Connection as JdbcConnection

class ConnectionTest {
    private val readWrite: JdbcConnection = mockk()
    private val readOnly: JdbcConnection = mockk()
    private val stmt: PreparedStatement = mockk()
    private val table = AddressTable()
    private val sfTable = SoftDeleteAddressTable()

    val connection = DummyConnection(readWrite, readOnly, null)

    @Test
    fun `given select params when table is not soft delete then run query according to params`() {
        every { readOnly.prepareStatement(any()) } returns stmt
        every { stmt.setString(any(), any()) } just runs
        every { stmt.executeQuery() } returns mockk()

        connection.query(
            table = table,
            where = where(table.id equalsTo "random-address"),
            order = mapOf(table.id to Order.ASC),
        )

        verify(exactly = 1) {
            readOnly.prepareStatement(
                """
                SELECT * FROM addresses
                    WHERE id = ?
                    ORDER BY id ASC
            """.trimIndent()
            )
        }
        verify(exactly = 1) { stmt.setString(1, "random-address") }
        verify(exactly = 1) { stmt.executeQuery() }
    }

    @Test
    fun `given select params when table is soft delete then query includes deleted_at field`() {
        every { readOnly.prepareStatement(any()) } returns stmt
        every { stmt.executeQuery() } returns mockk()

        connection.query(
            table = sfTable,
            where = emptyWhere(),
            order = mapOf(sfTable.id to Order.DESC),
        )

        verify(exactly = 1) {
            readOnly.prepareStatement(
                """
                SELECT * FROM addresses
                    WHERE deleted_at IS NULL
                    ORDER BY id DESC
            """.trimIndent()
            )
        }
        verify(exactly = 1) { stmt.executeQuery() }
    }

    @Test
    fun `given a table and params then run insert statement`() {
        every { readWrite.prepareStatement(any()) } returns stmt
        every { stmt.setString(any(), any()) } just runs
        every { stmt.execute() } returns true
        val params = listOf(Param(table.id, "address-id"), Param(table.number, "1234X"))

        connection.insert(table, params)

        verify(exactly = 1) {
            readWrite.prepareStatement(
                """
                INSERT INTO addresses (id, number)
                    VALUES (?, ?)
            """.trimIndent()
            )
        }
        verify(exactly = 1) { stmt.setString(1, "address-id") }
        verify(exactly = 1) { stmt.setString(2, "1234X") }
        verify(exactly = 1) { stmt.execute() }
    }

    @Test
    fun `given a table and its params when delete gets called then run delete statement`() {
        every { readWrite.prepareStatement(any()) } returns stmt
        every { stmt.setString(any(), any()) } just runs
        every { stmt.execute() } returns true

        connection.delete(
            table = table,
            where = where(table.id equalsTo "address-id"),
        )

        verify(exactly = 1) {
            readWrite.prepareStatement(
                """
                DELETE FROM addresses
                    WHERE id = ?
            """.trimIndent()
            )
        }
        verify(exactly = 1) { stmt.setString(1, "address-id") }
        verify(exactly = 1) { stmt.execute() }
    }

    @Test
    fun `given a soft delete table when delete gets called then run update statement`() {
        freezeClockAt("2022-11-14T18:25:10.000Z")
        every { readWrite.prepareStatement(any()) } returns stmt
        every { stmt.setTimestamp(any(), any()) } just runs
        every { stmt.setString(any(), any()) } just runs
        every { stmt.execute() } returns true

        connection.delete(table = sfTable, where = where(table.id equalsTo "address-id"))

        verify(exactly = 1) {
            readWrite.prepareStatement(
                """
                UPDATE addresses
                    SET deleted_at = ?
                    WHERE id = ?
            """.trimIndent()
            )
        }

        verify(exactly = 1) { stmt.setTimestamp(1, Timestamp.valueOf(("2022-11-14 18:25:10"))) }
        verify(exactly = 1) { stmt.setString(2, "address-id") }
        verify(exactly = 1) { stmt.execute() }
    }

    @Test
    fun `given a query and conditions then run query statement`() {
        every { readOnly.prepareStatement(any()) } returns stmt
        every { stmt.setBoolean(any(), any()) } just runs
        every { stmt.executeQuery() } returns mockk()
        val sql = "SELECT * FROM addresses WHERE main = ?"
        val params = where(table.main equalsTo true).params()

        connection.query(sql, params)

        verify(exactly = 1) { readOnly.prepareStatement("SELECT * FROM addresses WHERE main = ?") }
        verify(exactly = 1) { stmt.setBoolean(1, true) }
        verify(exactly = 1) { stmt.executeQuery() }
    }

    @Test
    fun `given a statement and conditions then run execute statement`() {
        every { readWrite.prepareStatement(any()) } returns stmt
        every { stmt.setString(any(), any()) } just runs
        every { stmt.execute() } returns true
        val sql = "UPDATE FROM addresses WHERE id != ?"
        val params = where(table.id differentOf "foo-bar").params()

        connection.execute(sql, params)

        verify(exactly = 1) { readWrite.prepareStatement("UPDATE FROM addresses WHERE id != ?") }
        verify(exactly = 1) { stmt.setString(1, "foo-bar") }
        verify(exactly = 1) { stmt.execute() }
    }
}
