package io.tcds.orm.driver

import io.tcds.orm.Column
import io.tcds.orm.OrmResultSet
import io.tcds.orm.Param
import io.tcds.orm.Table
import io.tcds.orm.extension.columns
import io.tcds.orm.extension.marks
import io.tcds.orm.extension.toOrderByStatement
import io.tcds.orm.statement.Limit
import io.tcds.orm.statement.Order
import io.tcds.orm.statement.Statement
import org.slf4j.Logger
import java.sql.PreparedStatement
import java.sql.Connection as JdbcConnection

interface Connection {
    val readOnly: JdbcConnection
    val readWrite: JdbcConnection
    val logger: Logger?

    fun <E> query(
        table: Table<E>,
        where: Statement,
        order: Map<Column<E, *>, Order>,
        limit: Int? = null,
        offset: Int? = null,
    ): Sequence<OrmResultSet> {
        val tableWhere = if (table.softDelete) where.getSoftDeleteStatement<E>() else where

        val sql = """
            SELECT * FROM ${table.tableName}
                ${tableWhere.toSql()}
                ${order.toOrderByStatement()}
                ${Limit(limit, offset)}
        """.trimIndent().trim()

        return query(sql, tableWhere.params())
    }

    fun <E> insert(table: Table<E>, params: List<Param<*, *>> = emptyList()) {
        val sql = """
            INSERT INTO ${table.tableName} (${params.columns()})
                VALUES (${params.marks()})
        """.trimIndent()

        execute(sql, params)
    }

    fun <E> delete(table: Table<E>, where: Statement) {
        when (table.softDelete) {
            false -> {
                val sql = """
                    DELETE FROM ${table.tableName}
                        ${where.toSql()}
                """.trimIndent()

                execute(sql, where.params())
            }
            true -> {
                val sql = """
                    UPDATE ${table.tableName}
                        SET deleted_at = ?
                        ${where.toSql()}
                """.trimIndent()

                execute(sql, where.getSoftDeleteQueryParams<E>())
            }
        }
    }

    fun query(sql: String, params: List<Param<*, *>> = emptyList()): Sequence<OrmResultSet> {
        val stmt = prepare(readOnly, sql, params)
        val result = stmt.executeQuery()
        if (logger !== null) ConnectionLogger.select(logger!!, sql, params)

        return sequence {
            while (result.next()) {
                yield(OrmResultSet(result))
            }
        }
    }

    fun execute(sql: String, params: List<Param<*, *>> = emptyList()): Boolean {
        val stmt = prepare(readWrite, sql, params)
        if (logger !== null) ConnectionLogger.execute(logger!!, sql, params)

        return stmt.execute()
    }

    private fun prepare(conn: JdbcConnection, sql: String, params: List<Param<*, *>> = emptyList()): PreparedStatement {
        val stmt = conn.prepareStatement(sql) ?: throw Exception("Failed to prepare sql statement")
        params.forEachIndexed { index, param -> param.bind((index + 1), stmt) }

        return stmt
    }
}
