package fixtures.driver

import io.tcds.orm.driver.Connection
import org.slf4j.Logger
import java.sql.Connection as JdbcConnection

class DummyConnection(
    override val readWrite: JdbcConnection,
    override val readOnly: JdbcConnection,
    override val logger: Logger?,
) : Connection
