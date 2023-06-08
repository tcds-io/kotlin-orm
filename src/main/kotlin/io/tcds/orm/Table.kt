package io.tcds.orm

import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.*
import io.tcds.orm.statement.Limit
import io.tcds.orm.statement.Statement

abstract class Table<E>(
    private val connection: Connection,
    private val table: String,
    private val softDelete: Boolean = false,
) : ResultSetEntry<E> {
    val columns = mutableListOf<Column<E, *>>()

    fun insert(vararg entries: E) = entries.forEach {
        val params = params(it)
        val sql = "INSERT INTO $table (${params.columns()}) VALUES (${params.marks()})"

        connection.write(sql, params)
    }

    fun loadBy(where: Statement, order: OrderStatement<E> = emptyList()): E? = findBy(
        where = where,
        order = order,
        limit = 1,
    ).firstOrNull()

    fun loadByQuery(sql: String, params: List<Param<*, *>> = emptyList()): E? {
        return connection
            .read(sql, params)
            .firstOrNull()
            ?.let { entry(it) }
    }

    fun findBy(
        where: Statement,
        order: OrderStatement<E> = emptyList(),
        limit: Int? = null,
        offset: Int? = null,
    ): Sequence<E> {
        val tableWhere = when (softDelete) {
            true -> where.getSoftDeleteStatement<E>()
            false -> where
        }

        val whereStmt = tableWhere.toStmt()
        val orderStmt = order.toOrderByStatement()
        val limitStmt = Limit(limit, offset).toStmt()
        val sql = "SELECT * FROM $table $whereStmt $orderStmt $limitStmt".trimSpaces()

        return connection
            .read(sql, tableWhere.params())
            .map { entry(it) }
    }

    fun findByQuery(sql: String, params: List<Param<*, *>> = emptyList()) = connection
        .read(sql, params)
        .map { entry(it) }

    fun exists(where: Statement): Boolean = loadBy(where) != null

    fun delete(where: Statement) {
        when (softDelete) {
            false -> connection.write(
                "DELETE FROM $table ${where.toStmt()}",
                where.params(),
            )
            true -> connection.write(
                "UPDATE $table SET deleted_at = ? ${where.toStmt()}",
                where.getSoftDeleteQueryParams<E>(),
            )
        }
    }

    fun update(params: List<Param<*, *>>, where: Statement) {
        val tableWhere = when (softDelete) {
            true -> where.getSoftDeleteStatement<E>()
            else -> where
        }

        val columnsMarks = params.columnsEqualMarks()
        val whereStmt = tableWhere.toStmt()
        val sql = "UPDATE $table SET $columnsMarks $whereStmt".trimSpaces()

        connection.write(sql, (params + where.params()))
    }

    fun values(entry: E): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        columns.forEach { map[it.name] = it.valueOf(entry) }

        return map
    }
}
