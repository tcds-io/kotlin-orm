package fixtures.connection

import io.tcds.orm.connection.NestedTransactionConnection
import java.sql.Connection as JdbcConnection

class DummyNestedTransactionConnection(
    readOnly: JdbcConnection,
    readWrite: JdbcConnection,
) : NestedTransactionConnection(readOnly = readOnly, readWrite = readWrite, logger = null)
