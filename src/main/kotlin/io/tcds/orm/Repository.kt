package io.tcds.orm

import io.tcds.orm.driver.Connection
import io.tcds.orm.extension.emptyWhere
import io.tcds.orm.statement.Order
import io.tcds.orm.statement.Statement

open class Repository<E>(private val table: Table<E>, private val connection: Connection) {
    fun delete(where: Statement) = connection.delete(table, where)
    fun exists(where: Statement): Boolean = loadBy(where) != null
    fun insert(vararg entries: E) = entries.forEach { connection.insert(table, table.params(it)) }

    fun loadByQuery(sql: String, params: List<Param<*, *>> = emptyList()): E? = connection.query(
        sql,
        params
    ).firstOrNull()?.let { table.entry(it) }

    fun loadBy(where: Statement, order: Map<Column<E, *>, Order> = emptyMap()): E? = connection.query(
        table = table,
        where = where,
        order = order,
        limit = 1
    ).firstOrNull()?.let { table.entry(it) }

    fun select(
        where: Statement = emptyWhere(),
        order: Map<Column<E, *>, Order> = emptyMap(),
        limit: Int? = null,
        offset: Int? = null,
    ): Sequence<E> = connection.query(
        table = table,
        where = where,
        order = order,
        limit = limit,
        offset = offset,
    ).map { table.entry(it) }

    fun selectByQuery(sql: String, params: List<Param<*, *>> = emptyList()): Sequence<E> = connection.query(
        sql = sql,
        params = params,
    ).map { table.entry(it) }
}
