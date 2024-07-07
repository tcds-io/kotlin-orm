package fixtures.connection

import io.mockk.*
import io.tcds.orm.connection.Connection
import io.tcds.orm.connection.GenericConnection
import io.tcds.orm.connection.ResilientConnection
import org.slf4j.Logger
import java.sql.Connection as JdbcConnection

class DummyConnection(
    readWrite: ResilientConnection,
    readOnly: ResilientConnection,
    logger: Logger? = null,
) : GenericConnection(readOnly, readWrite, logger) {
    override val writer: JdbcConnection = mockk()

    companion object {
        fun dummy(): Connection = DummyConnection(
            ResilientConnection.reconnectable { mockk() },
            ResilientConnection.reconnectable { mockk() },
        )
    }
}
