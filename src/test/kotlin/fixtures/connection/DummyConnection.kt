package fixtures.connection

import io.mockk.mockk
import io.tcds.orm.connection.Connection
import io.tcds.orm.connection.GenericConnection
import org.slf4j.Logger
import java.sql.Connection as JdbcConnection

class DummyConnection(
    readWrite: JdbcConnection,
    readOnly: JdbcConnection,
    logger: Logger?,
) : GenericConnection(readOnly, readWrite, logger) {
    companion object {
        fun dummy(): Connection {
            val ro: JdbcConnection = mockk()
            val rw: JdbcConnection = mockk()

            return DummyConnection(rw, ro, null)
        }
    }
}
