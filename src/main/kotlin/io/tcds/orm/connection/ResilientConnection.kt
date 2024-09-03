package io.tcds.orm.connection

import org.slf4j.Logger
import java.sql.Connection
import java.sql.Connection as JdbcConnection

interface ResilientConnection {
    fun instance(): JdbcConnection

    companion object {
        fun reconnectable(logger: Logger? = null, factory: () -> Connection) = ReconnectableConnection(logger, factory)
    }
}
