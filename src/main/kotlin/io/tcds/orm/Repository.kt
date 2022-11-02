package io.tcds.orm

import io.tcds.orm.driver.Connection
import io.tcds.orm.statement.Clause
import io.tcds.orm.statement.Order

open class Repository<E>(private val table: Table<E>, private val connection: Connection) {
    fun loadBy(where: List<Clause>, order: Map<Column<E, *>, Order> = emptyMap()): E? = selectOne(where, order)

    fun loadByQuery(sql: String, params: List<Clause>): E? = connection.select(sql, params).firstOrNull()?.let {
        table.entity(it)
    }

    fun exists(where: List<Clause>): Boolean = selectOne(where) != null

    fun selectOne(where: List<Clause>, order: Map<Column<E, *>, Order> = emptyMap()): E? = connection.select(
        table = table.table,
        where = where,
        order = order,
        limit = 1
    ).firstOrNull()?.let { table.entity(it) }

    fun select(
        where: List<Clause> = emptyList(),
        order: Map<Column<E, *>, Order> = emptyMap(),
        limit: Int? = null,
        offset: Int? = null,
    ): Sequence<E> = connection.select(
        table = table.table,
        where = where,
        order = order,
        limit = limit,
        offset = offset,
    ).map { table.entity(it) }

    fun insert(vararg entries: E) = entries.forEach { connection.insert(table.table, table.params(it)) }
}
