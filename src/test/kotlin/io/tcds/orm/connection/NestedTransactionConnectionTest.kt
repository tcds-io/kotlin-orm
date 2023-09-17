package io.tcds.orm.connection

import fixtures.connection.DummyNestedTransactionConnection
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Statement
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
        every { readWrite.prepareStatement(any(), Statement.RETURN_GENERATED_KEYS) } returns stmt
        every { stmt.execute() } returns true

        runBlocking {
            connection.begin()
            connection.commit()
        }

        verify(exactly = 1) { readWrite.prepareStatement("BEGIN", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("COMMIT", Statement.RETURN_GENERATED_KEYS) }
    }

    @Test
    fun `given a connection when begin and commit gets called multiple times then run store and release savepoint`() {
        val connection = DummyNestedTransactionConnection(readOnly, readWrite)
        every { readWrite.prepareStatement(any(), Statement.RETURN_GENERATED_KEYS) } returns stmt
        every { stmt.execute() } returns true

        runBlocking {
            connection.begin()
            connection.begin()
            connection.begin()
            connection.commit()
            connection.commit()
            connection.commit()
        }

        verify(exactly = 1) { readWrite.prepareStatement("BEGIN", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("SAVEPOINT LEVEL1", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("SAVEPOINT LEVEL2", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("RELEASE SAVEPOINT LEVEL2", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("RELEASE SAVEPOINT LEVEL1", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("COMMIT", Statement.RETURN_GENERATED_KEYS) }
    }

    @Test
    fun `given a connection when begin then rollback gets called then run begin and rollback statements`() {
        val connection = DummyNestedTransactionConnection(readOnly, readWrite)
        every { readWrite.prepareStatement(any(), Statement.RETURN_GENERATED_KEYS) } returns stmt
        every { stmt.execute() } returns true

        runBlocking {
            connection.begin()
            connection.rollback()
        }

        verify(exactly = 1) { readWrite.prepareStatement("BEGIN", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("ROLLBACK", Statement.RETURN_GENERATED_KEYS) }
    }

    @Test
    fun `given a connection when begin and rollback gets called multiple times then run store and release savepoint`() {
        val connection = DummyNestedTransactionConnection(readOnly, readWrite)
        every { readWrite.prepareStatement(any(), Statement.RETURN_GENERATED_KEYS) } returns stmt
        every { stmt.execute() } returns true

        runBlocking {
            connection.begin()
            connection.begin()
            connection.begin()
            connection.rollback()
            connection.rollback()
            connection.rollback()
        }

        verify(exactly = 1) { readWrite.prepareStatement("BEGIN", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("SAVEPOINT LEVEL1", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("SAVEPOINT LEVEL2", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("ROLLBACK TO SAVEPOINT LEVEL2", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("ROLLBACK TO SAVEPOINT LEVEL1", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { readWrite.prepareStatement("ROLLBACK", Statement.RETURN_GENERATED_KEYS) }
    }
}
