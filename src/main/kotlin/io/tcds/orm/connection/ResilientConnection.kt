package io.tcds.orm.connection

import java.sql.Connection
import java.sql.Connection as JdbcConnection

interface ResilientConnection {
    fun instance(): JdbcConnection

    companion object {
        fun reconnectable(factory: () -> Connection) = ReconnectableResilientConnection(factory)
    }
}
