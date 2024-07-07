package fixtures.connection

import io.mockk.*
import io.tcds.orm.connection.Connection
import io.tcds.orm.connection.GenericConnection
import io.tcds.orm.connection.ResilientConnection
import org.slf4j.Logger

class DummyConnection(
    readWrite: ResilientConnection,
    readOnly: ResilientConnection,
    logger: Logger? = null,
) : GenericConnection(readOnly, readWrite, logger) {
    companion object {
        fun dummy(): Connection = DummyConnection(
            ResilientConnection.reconnectable { mockk() },
            ResilientConnection.reconnectable { mockk() },
        )
    }
}
