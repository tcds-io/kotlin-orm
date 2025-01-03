package io.tcds.orm

import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.*
import io.tcds.orm.statement.Limit
import io.tcds.orm.statement.Statement
import java.sql.Statement as JdbcStatement

abstract class Table<E>(
    val connection: Connection,
    val table: String,
    private val softDelete: Boolean = false,
) : ResultSetEntry<E> {
    val columns = mutableListOf<Column<E, *>>()

    val cols: String
        get() = columns.joinToString(",") { it.name }

    val marks: String
        get() = columns.joinToString(",") { "?" }

    fun insert(vararg entries: E): List<JdbcStatement> {
        return entries.map { doInsert(listOf(it)) }
    }

    fun bulkInsert(entries: List<E>): JdbcStatement {
        return doInsert(entries)
    }

    fun bulkInsertIgnore(entries: List<E>): JdbcStatement {
        return doInsert(entries, ignore = true)
    }

    fun loadBy(where: Statement, order: OrderStatement<E> = emptyList()): E? = findBy(
        where = where,
        order = order,
        limit = 1,
    ).firstOrNull()

    fun loadByQuery(sql: String, vararg params: Param<*>): E? = connection
        .read(sql, params.toList())
        .firstOrNull()
        ?.let { entry(it) }

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
        val params = tableWhere.params()

        return connection
            .read(sql, params)
            .map { entry(it) }
    }

    fun findByQuery(sql: String, vararg params: Param<*>) = connection
        .read(sql, params.toList())
        .map { entry(it) }

    fun exists(where: Statement): Boolean = loadBy(where) != null

    fun delete(where: Statement) = when (softDelete) {
        false -> connection.write(
            "DELETE FROM $table ${where.toStmt()}",
            where.params(),
        )

        true -> connection.write(
            "UPDATE $table SET deleted_at = ? ${where.toStmt()}",
            where.getSoftDeleteQueryParams(),
        )
    }

    fun update(params: List<Param<*>>, where: Statement): JdbcStatement {
        val tableWhere = when (softDelete) {
            true -> where.getSoftDeleteStatement<E>()
            else -> where
        }

        val columnsMarks = params.columnsEqualMarks()
        val whereStmt = tableWhere.toStmt()
        val sql = "UPDATE $table SET $columnsMarks $whereStmt".trimSpaces()

        return connection.write(sql, params + where.params())
    }

    fun values(entry: E): Map<String, Any?> = columns.associate {
        it.name to it.entryParam(entry).plain()
    }

    fun ddl() = StringBuilder().apply {
        appendLine("CREATE TABLE `$table` (")
        appendLine("    ${columns.joinToString(",\n    ") { it.ddl() }}")
        appendLine(");")
    }.trim()

    private fun doInsert(entries: List<E>, ignore: Boolean = false): JdbcStatement {
        val insert = when (ignore) {
            true -> "INSERT IGNORE"
            false -> "INSERT"
        }
        val params = mutableListOf<Param<*>>()

        val sql = StringBuilder().apply {
            appendLine("$insert INTO $table ($cols)")
            appendLine("VALUES")
            appendLine(entries.joinToString(",\n") { params.addAll(params(it)).let { "($marks)" } })
        }.toString().trim()

        return connection.write(sql, params)
    }
}
