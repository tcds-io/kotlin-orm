package fixtures

import io.tcds.orm.Param
import io.tcds.orm.connection.Connection
import io.tcds.orm.connection.GenericConnection
import kotlinx.coroutines.runBlocking

fun Connection.coWrite(sql: String, params: List<Param<*, *>> = emptyList()): Boolean {
    return runBlocking { write(sql, params) }
}

fun GenericConnection.coClose() = runBlocking { close() }
