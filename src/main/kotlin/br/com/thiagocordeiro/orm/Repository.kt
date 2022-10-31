package br.com.thiagocordeiro.orm

import br.com.thiagocordeiro.orm.clauses.Clause
import br.com.thiagocordeiro.orm.clauses.Order

open class Repository<E>(private val table: Table<E>, private val connection: Connection) {
    fun loadBy(where: List<Clause>, order: Map<Column<*>, Order> = emptyMap()): E? = selectOne(where, order)
    // fun loadByQuery(query: String, bindings: Map<String, String>): E? = ...

    fun exists(where: List<Clause>): Boolean = selectOne(where) != null

    fun selectOne(where: List<Clause>, order: Map<Column<*>, Order> = emptyMap()): E? {
        val first = connection.select(
            table = table.name,
            where = where,
            order = order,
            limit = 1
        ).firstOrNull() ?: return null

        return table.toEntity(first)
    }

    fun select(
        where: List<Clause>,
        order: Map<Column<*>, Order> = emptyMap(),
        limit: Int?,
        offset: Int?,
    ): Sequence<E> {
        val list = connection.select(
            table = table.name,
            where = where,
            order = order,
            limit = limit,
            offset = offset,
        )

        return list.map { table.toEntity(it) }
    }
}
