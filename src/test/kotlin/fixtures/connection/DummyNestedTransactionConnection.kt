package fixtures.connection

import io.tcds.orm.connection.NestedTransactionConnection
import io.tcds.orm.connection.ResilientConnection

class DummyNestedTransactionConnection(
    readOnly: ResilientConnection,
    readWrite: ResilientConnection,
) : NestedTransactionConnection(readOnly, readWrite, null)
