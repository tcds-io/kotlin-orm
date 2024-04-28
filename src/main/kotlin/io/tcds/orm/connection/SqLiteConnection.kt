package io.tcds.orm.connection

import org.slf4j.Logger

open class SqLiteConnection(
    private val connection: ResilientConnection,
    logger: Logger?,
) : GenericConnection(connection, connection, logger) {
    override fun close() {
        connection.instance().close()
    }
}
