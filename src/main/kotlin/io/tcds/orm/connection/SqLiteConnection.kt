package io.tcds.orm.connection

import org.slf4j.Logger
import java.sql.Connection as JdbcConnection

open class SqLiteConnection(
    private val connection: JdbcConnection,
    logger: Logger?,
) : GenericConnection(connection, connection, logger) {
    override fun close() {
        connection.close()
    }
}
