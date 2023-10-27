package io.tcds.orm.connection

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Param
import java.sql.Statement

interface Connection {
    fun begin(): Statement
    fun commit(): Statement
    fun rollback(): Statement
    fun <T> transaction(block: Connection.() -> T): T
    fun read(sql: String, params: List<Param<*>> = emptyList()): Sequence<OrmResultSet>
    fun write(sql: String, params: List<Param<*>> = emptyList()): Statement
}
