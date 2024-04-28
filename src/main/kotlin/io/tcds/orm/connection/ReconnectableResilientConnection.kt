package io.tcds.orm.connection

import java.sql.Connection

open class ReconnectableResilientConnection(
    private val factory: () -> Connection,
) : ResilientConnection {
    private var connection: Connection? = null

    override fun instance(): Connection {
        if (connection == null || connection!!.isClosed) connection = factory()

        return connection!!
    }
}
