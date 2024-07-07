package io.tcds.orm.connection

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Param
import java.sql.Statement
import java.sql.Connection as JdbcConnection

interface Connection {
    val reader: JdbcConnection
    val writer: JdbcConnection

    fun begin(): Statement
    fun commit(): Statement
    fun rollback(): Statement
    fun <T> transaction(block: () -> T): T
    fun read(sql: String, params: List<Param<*>> = emptyList()): Sequence<OrmResultSet>
    fun write(sql: String, params: List<Param<*>> = emptyList()): Statement
}
