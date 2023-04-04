package io.tcds.orm.connection

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Param

interface Connection {
    fun begin(): Boolean
    fun commit(): Boolean
    fun rollback(): Boolean
    fun <T> transaction(block: () -> T)
    fun read(sql: String, params: List<Param<*, *>> = emptyList()): Sequence<OrmResultSet>
    fun write(sql: String, params: List<Param<*, *>> = emptyList()): Boolean
}
