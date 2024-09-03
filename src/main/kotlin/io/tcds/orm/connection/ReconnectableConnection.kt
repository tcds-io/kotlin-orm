package io.tcds.orm.connection

import org.slf4j.Logger
import java.sql.Connection

class ReconnectableConnection(
    private val logger: Logger? = null,
    private val factory: () -> Connection,
) : ResilientConnection {
    private var connection: Connection? = null

    override fun instance(): Connection {
        val status = listOf(
            connection == null,
            connection?.isClosed,
            connection?.isValid(5) == false,
        )
        val requiresCreation = status.any { it == true }

        if (requiresCreation) {
            connection?.close().apply { logger?.info("Closed active connection") }
            connection = factory().apply { logger?.info("Created new connection") }
        }

        return connection!!
    }
}
