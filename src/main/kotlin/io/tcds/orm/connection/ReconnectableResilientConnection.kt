package io.tcds.orm.connection

import java.sql.Connection

class ReconnectableResilientConnection(
    private val factory: () -> Connection,
) : ResilientConnection {
    private var connection: Connection? = null

    override fun instance(): Connection {
        val requiresCreation = listOf(
            connection == null,
            connection?.isClosed,
            connection?.isValid(5) == false,
        ).any { it == true }

        if (requiresCreation) connection = factory()

        return connection!!
    }
}
