package io.tcds.orm.connection

import io.mockk.*
import io.tcds.orm.extension.emptyParams
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.Statement

class GenericConnectionTest {
    private val read: Connection = mockk()
    private val write: Connection = mockk()
    private val stmt: PreparedStatement = mockk()

    private val connection = GenericConnection(
        ResilientConnection.reconnectable { read },
        ResilientConnection.reconnectable { write },
        null,
    )

    init {
        every { read.isValid(any()) } returns true
        every { write.isValid(any()) } returns true
        every { read.isClosed } returns false
        every { write.isClosed } returns false
    }

    @Test
    fun `given a connection when transaction gets called and no exception is thrown then commit the transaction`() {
        every { write.prepareStatement(any(), Statement.RETURN_GENERATED_KEYS) } returns stmt
        every { stmt.execute() } returns true

        connection.transaction {}

        verify(exactly = 1) { write.prepareStatement("BEGIN", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { write.prepareStatement("COMMIT", Statement.RETURN_GENERATED_KEYS) }
    }

    @Test
    fun `given a connection when transaction gets called and an exception is thrown then rollback the transaction`() {
        every { write.prepareStatement(any(), Statement.RETURN_GENERATED_KEYS) } returns stmt
        every { stmt.execute() } returns true

        val ex = assertThrows<Exception> { connection.transaction { throw Exception("a weird error occurred") } }

        Assertions.assertEquals("a weird error occurred", ex.message)
        verify(exactly = 1) { write.prepareStatement("BEGIN", Statement.RETURN_GENERATED_KEYS) }
        verify(exactly = 1) { write.prepareStatement("ROLLBACK", Statement.RETURN_GENERATED_KEYS) }
    }

    @Test
    fun `run stmt in the read connection`() {
        every { read.prepareStatement(any(), Statement.RETURN_GENERATED_KEYS) } returns stmt
        every { stmt.executeQuery() } returns mockk()

        connection.read("SELECT * FROM foo WHERE 0=1", emptyParams())

        verify(exactly = 1) { read.prepareStatement("SELECT * FROM foo WHERE 0=1", Statement.RETURN_GENERATED_KEYS) }
    }

    @Test
    fun `run stmt in the write connection`() {
        every { write.prepareStatement(any(), Statement.RETURN_GENERATED_KEYS) } returns stmt
        every { stmt.execute() } returns true

        connection.write("DELETE FROM foo WHERE 0=1", emptyParams())

        verify(exactly = 1) { write.prepareStatement("DELETE FROM foo WHERE 0=1", Statement.RETURN_GENERATED_KEYS) }
    }
}
