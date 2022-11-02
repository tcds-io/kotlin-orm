package io.tcds.orm.driver

import io.tcds.orm.Column
import io.tcds.orm.OrmResultSet
import io.tcds.orm.Param
import io.tcds.orm.extension.*
import io.tcds.orm.statement.Condition
import io.tcds.orm.statement.Limit
import io.tcds.orm.statement.Order
import org.slf4j.Logger
import java.sql.PreparedStatement
import java.sql.Connection as JdbcConnection

interface Connection {
    val readOnly: JdbcConnection
    val readWrite: JdbcConnection
    val logger: Logger?

    fun <E> select(
        table: String,
        conditions: List<Condition>,
        order: Map<Column<E, *>, Order>,
        limit: Int? = null,
        offset: Int? = null,
    ): Sequence<OrmResultSet> {
        val sql = """
            SELECT * FROM $table
                ${conditions.toWhereStatement()}
                ${order.toOrderByStatement()}
                ${Limit(limit, offset)}
        """.trimIndent()

        return select(sql, conditions.toParams())
    }

    fun select(sql: String, params: List<Param<*, *>> = emptyList()): Sequence<OrmResultSet> {
        val stmt = prepare(readOnly, sql, params)
        val result = stmt.executeQuery()
        if (logger !== null) ConnectionLogger.select(logger!!, sql, params)

        return sequence {
            while (result.next()) {
                yield(OrmResultSet(result))
            }
        }
    }

    fun insert(table: String, params: List<Param<*, *>> = emptyList()) {
        val sql = """
            INSERT INTO $table (${params.columns()})
                VALUES (${params.marks()})
        """.trimIndent()

        execute(sql, params)
    }

    fun delete(table: String, conditions: List<Condition>) {
        val sql = "DELETE FROM $table ${conditions.toWhereStatement()}"

        execute(sql, conditions.toParams())
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
