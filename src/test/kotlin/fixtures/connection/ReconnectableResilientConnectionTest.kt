package fixtures.connection

import io.mockk.every
import io.mockk.mockk
import io.tcds.orm.connection.ResilientConnection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.Connection as JdbcConnection

class ReconnectableResilientConnectionTest {
    private val connection = mockk<JdbcConnection>()
    private var calls = 0

    @Test
    fun `when instance is null then create from the factory`() {
        val connection = ResilientConnection.reconnectable {
            calls++
            connection
        }

        val instance = connection.instance()

        Assertions.assertEquals(1, calls)
        Assertions.assertInstanceOf(JdbcConnection::class.java, instance)
    }

    @Test
    fun `when instance is closed then create from the factory`() {
        every { connection.isClosed } returns true
        every { connection.isValid(any()) } returns true
        val connection = ResilientConnection.reconnectable {
            calls++
            connection
        }

        val instance = connection.instance()
        connection.instance()
        connection.instance()

        Assertions.assertEquals(3, calls)
        Assertions.assertInstanceOf(JdbcConnection::class.java, instance)
    }

    @Test
    fun `when instance is not valid then create from the factory`() {
        every { connection.isClosed } returns false
        every { connection.isValid(any()) } returns false
        val connection = ResilientConnection.reconnectable {
            calls++
            connection
        }

        val instance = connection.instance()
        connection.instance()
        connection.instance()

        Assertions.assertEquals(3, calls)
        Assertions.assertInstanceOf(JdbcConnection::class.java, instance)
    }
}
