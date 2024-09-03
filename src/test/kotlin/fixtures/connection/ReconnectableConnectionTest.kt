package fixtures.connection

import io.mockk.*
import io.tcds.orm.connection.ResilientConnection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import java.sql.Connection as JdbcConnection

class ReconnectableConnectionTest {
    private val logger = mockk<Logger>()
    private val connection = mockk<JdbcConnection>()
    private var calls = 0

    init {
        every { logger.info(any()) } just runs
    }

    @Test
    fun `when instance is null then create from the factory`() {
        val connection = ResilientConnection.reconnectable(logger) {
            calls++
            connection
        }

        val instance = connection.instance()

        Assertions.assertEquals(1, calls)
        Assertions.assertInstanceOf(JdbcConnection::class.java, instance)
        verify(exactly = 1) { logger.info("Created new connection") }
    }

    @Test
    fun `when instance is closed then create from the factory`() {
        every { connection.isClosed } returns true
        every { connection.isValid(any()) } returns true
        every { connection.close() } just runs
        val connection = ResilientConnection.reconnectable(logger) {
            calls++
            connection
        }

        val instance = connection.instance()
        connection.instance()
        connection.instance()

        Assertions.assertEquals(3, calls)
        Assertions.assertInstanceOf(JdbcConnection::class.java, instance)
        verifySequence {
            // first
            logger.info("Closed active connection")
            logger.info("Created new connection")
            // second
            logger.info("Closed active connection")
            logger.info("Created new connection")
            // third
            logger.info("Closed active connection")
            logger.info("Created new connection")
        }
    }

    @Test
    fun `when instance is not valid then create from the factory`() {
        every { connection.isClosed } returns false
        every { connection.isValid(any()) } returns false
        every { connection.close() } just runs
        val connection = ResilientConnection.reconnectable(logger) {
            calls++
            connection
        }

        val instance = connection.instance()
        connection.instance()
        connection.instance()

        Assertions.assertEquals(3, calls)
        Assertions.assertInstanceOf(JdbcConnection::class.java, instance)
        verifySequence {
            // first
            logger.info("Closed active connection")
            logger.info("Created new connection")
            // second
            logger.info("Closed active connection")
            logger.info("Created new connection")
            // third
            logger.info("Closed active connection")
            logger.info("Created new connection")
        }
    }
}
