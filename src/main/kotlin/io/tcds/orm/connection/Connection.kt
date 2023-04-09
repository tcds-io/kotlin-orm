package io.tcds.orm.connection

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Param

interface Connection {
    suspend fun begin(): Boolean
    suspend fun commit(): Boolean
    suspend fun rollback(): Boolean
    suspend fun transaction(block: suspend () -> Unit)
    suspend fun read(sql: String, params: List<Param<*, *>> = emptyList()): Sequence<OrmResultSet>
    suspend fun write(sql: String, params: List<Param<*, *>> = emptyList()): Boolean
}
