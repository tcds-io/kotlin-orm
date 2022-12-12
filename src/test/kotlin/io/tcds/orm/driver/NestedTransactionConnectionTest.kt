package io.tcds.orm.driver

import fixtures.driver.DummyNestedTransactionConnection
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Connection as JdbcConnection

class NestedTransactionConnectionTest {
    private val readOnly: JdbcConnection = mockk()
    private val readWrite: JdbcConnection = mockk()
    private val stmt: PreparedStatement = mockk()

    init {
        every { readOnly.isValid(any()) } returns true
        every { readWrite.isValid(any()) } returns true
    }

    @Test
    fun `given a connection when begin then commit gets called then run begin and commit statements`() {
        val connection = DummyNestedTransactionConnection(readOnly, readWrite)
        every { readWrite.prepareStatement(any()) } returns stmt
        every { stmt.execute() } returns true

        connection.begin()
        connection.commit()

        verify(exactly = 1) { readWrite.prepareStatement("BEGIN") }
        verify(exactly = 1) { readWrite.prepareStatement("COMMIT") }
    }

    @Test
    fun `given a connection when begin and commit gets called multiple times then run store and release savepoint`() {
        val connection = DummyNestedTransactionConnection(readOnly, readWrite)
        every { readWrite.prepareStatement(any()) } returns stmt
        every { stmt.execute() } returns true

        connection.begin()
        connection.begin()
        connection.begin()
        connection.commit()
        connection.commit()
        connection.commit()

        verify(exactly = 1) { readWrite.prepareStatement("BEGIN") }
        verify(exactly = 1) { readWrite.prepareStatement("SAVEPOINT LEVEL1") }
        verify(exactly = 1) { readWrite.prepareStatement("SAVEPOINT LEVEL2") }
        verify(exactly = 1) { readWrite.prepareStatement("RELEASE SAVEPOINT LEVEL2") }
        verify(exactly = 1) { readWrite.prepareStatement("RELEASE SAVEPOINT LEVEL1") }
        verify(exactly = 1) { readWrite.prepareStatement("COMMIT") }
    }

    @Test
    fun `given a connection when begin then rollback gets called then run begin and rollback statements`() {
        val connection = DummyNestedTransactionConnection(readOnly, readWrite)
        every { readWrite.prepareStatement(any()) } returns stmt
        every { stmt.execute() } returns true

        connection.begin()
        connection.rollback()

        verify(exactly = 1) { readWrite.prepareStatement("BEGIN") }
        verify(exactly = 1) { readWrite.prepareStatement("ROLLBACK") }
    }

    @Test
    fun `given a connection when begin and rollback gets called multiple times then run store and release savepoint`() {
        val connection = DummyNestedTransactionConnection(readOnly, readWrite)
        every { readWrite.prepareStatement(any()) } returns stmt
        every { stmt.execute() } returns true

        connection.begin()
        connection.begin()
        connection.begin()
        connection.rollback()
        connection.rollback()
        connection.rollback()

        verify(exactly = 1) { readWrite.prepareStatement("BEGIN") }
        verify(exactly = 1) { readWrite.prepareStatement("SAVEPOINT LEVEL1") }
        verify(exactly = 1) { readWrite.prepareStatement("SAVEPOINT LEVEL2") }
        verify(exactly = 1) { readWrite.prepareStatement("ROLLBACK TO SAVEPOINT LEVEL2") }
        verify(exactly = 1) { readWrite.prepareStatement("ROLLBACK TO SAVEPOINT LEVEL1") }
        verify(exactly = 1) { readWrite.prepareStatement("ROLLBACK") }
    }

    @Test
    fun `given a connection when transaction gets called and no exception is thrown then commit the transaction`() {
        val connection = DummyNestedTransactionConnection(readOnly, readWrite)
        every { readWrite.prepareStatement(any()) } returns stmt
        every { stmt.execute() } returns true

        connection.transaction {}

        verify(exactly = 1) { readWrite.prepareStatement("BEGIN") }
        verify(exactly = 1) { readWrite.prepareStatement("COMMIT") }
    }

    @Test
    fun `given a connection when transaction gets called and an exception is thrown then rollback the transaction`() {
        val connection = DummyNestedTransactionConnection(readOnly, readWrite)
        every { readWrite.prepareStatement(any()) } returns stmt
        every { stmt.execute() } returns true

        connection.transaction { throw Exception("error") }

        verify(exactly = 1) { readWrite.prepareStatement("BEGIN") }
        verify(exactly = 1) { readWrite.prepareStatement("ROLLBACK") }
    }
}
