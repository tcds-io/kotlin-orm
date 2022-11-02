package io.tcds.orm

import io.tcds.orm.driver.Connection
import io.tcds.orm.statement.Condition
import io.tcds.orm.statement.Order

open class Repository<E>(private val table: Table<E>, private val connection: Connection) {
    fun delete(conditions: List<Condition>) = connection.delete(table.table, conditions)

    fun exists(conditions: List<Condition>): Boolean = loadBy(conditions) != null

    fun insert(vararg entries: E) = entries.forEach { connection.insert(table.table, table.params(it)) }

    fun loadByQuery(sql: String, params: List<Param<*, *>> = emptyList()): E? = connection.select(
        sql,
        params
    ).firstOrNull()?.let { table.entity(it) }

    fun loadBy(where: List<Condition>, order: Map<Column<E, *>, Order> = emptyMap()): E? = connection.select(
        table = table.table,
        conditions = where,
        order = order,
        limit = 1
    ).firstOrNull()?.let { table.entity(it) }

    fun select(
        conditions: List<Condition> = emptyList(),
        order: Map<Column<E, *>, Order> = emptyMap(),
        limit: Int? = null,
        offset: Int? = null,
    ): Sequence<E> = connection.select(
        table = table.table,
        conditions = conditions,
        order = order,
        limit = limit,
        offset = offset,
    ).map { table.entity(it) }

    fun selectByQuery(sql: String, params: List<Param<*, *>> = emptyList()): Sequence<E> = connection.select(
        sql = sql,
        params = params,
    ).map { table.entity(it) }
}
