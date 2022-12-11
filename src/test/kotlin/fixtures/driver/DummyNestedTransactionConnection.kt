package fixtures.driver

import io.tcds.orm.driver.NestedTransactionConnection
import java.sql.Connection as JdbcConnection

class DummyNestedTransactionConnection(
    readOnly: JdbcConnection,
    readWrite: JdbcConnection,
) : NestedTransactionConnection(readOnly = readOnly, readWrite = readWrite, logger = null)

