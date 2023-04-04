package io.tcds.orm.connection

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.tcds.orm.extension.emptyParams
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.sql.Connection
import java.sql.PreparedStatement

class GenericConnectionTest {
    private val read: Connection = mockk()
    private val write: Connection = mockk()
    private val stmt: PreparedStatement = mockk()

    private val connection = GenericConnection(read, write, null)

    init {
        every { read.isValid(any()) } returns true
        every { write.isValid(any()) } returns true
    }

    @Test
    fun `given a connection when transaction gets called and no exception is thrown then commit the transaction`() {
        every { write.prepareStatement(any()) } returns stmt
        every { stmt.execute() } returns true

        runBlocking { connection.transaction {} }

        verify(exactly = 1) { write.prepareStatement("BEGIN") }
        verify(exactly = 1) { write.prepareStatement("COMMIT") }
    }

    @Test
    fun `given a connection when transaction gets called and an exception is thrown then rollback the transaction`() {
        every { write.prepareStatement(any()) } returns stmt
        every { stmt.execute() } returns true

        val ex = assertThrows<Exception> {
            runBlocking { connection.transaction { throw Exception("a weird error occurred") } }
        }

        Assertions.assertEquals("a weird error occurred", ex.message)
        verify(exactly = 1) { write.prepareStatement("BEGIN") }
        verify(exactly = 1) { write.prepareStatement("ROLLBACK") }
    }

    @Test
    fun `run stmt in the read connection`() {
        every { read.prepareStatement(any()) } returns stmt
        every { stmt.executeQuery() } returns mockk()

        runBlocking { connection.read("SELECT * FROM foo WHERE 0=1", emptyParams()) }

        verify(exactly = 1) { read.prepareStatement("SELECT * FROM foo WHERE 0=1") }
    }

    @Test
    fun `run stmt in the write connection`() {
        every { write.prepareStatement(any()) } returns stmt
        every { stmt.execute() } returns true

        runBlocking { connection.write("DELETE FROM foo WHERE 0=1", emptyParams()) }

        verify(exactly = 1) { write.prepareStatement("DELETE FROM foo WHERE 0=1") }
    }
}
