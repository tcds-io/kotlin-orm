package io.tcds.orm.driver

import io.tcds.orm.Column
import io.tcds.orm.OrmResultSet
import io.tcds.orm.Param
import io.tcds.orm.extension.*
import io.tcds.orm.statement.Clause
import io.tcds.orm.statement.Limit
import io.tcds.orm.statement.Order
import java.sql.PreparedStatement
import java.sql.Connection as JdbcConnection

interface Connection {
    val readOnly: JdbcConnection
    val readWrite: JdbcConnection

    fun <E> select(
        table: String,
        where: List<Clause>,
        order: Map<Column<E, *>, Order>,
        limit: Int? = null,
        offset: Int? = null,
    ): Sequence<OrmResultSet> {
        val sql = """
            SELECT * FROM $table
                ${where.toWhereStatement()}
                ${order.toOrderByStatement()}
                ${Limit(limit, offset)}
        """.trimIndent()

        return select(sql, where)
    }

    fun select(sql: String, clauses: List<Clause> = emptyList()): Sequence<OrmResultSet> {
        val stmt = prepare(readOnly, sql, clauses.toParams())
        val result = stmt.executeQuery()

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

    fun execute(sql: String, params: List<Param<*, *>> = emptyList()): Boolean {
        val stmt = prepare(readWrite, sql, params)

        return stmt.execute()
    }

    private fun prepare(conn: JdbcConnection, sql: String, params: List<Param<*, *>> = emptyList()): PreparedStatement {
        val stmt = conn.prepareStatement(sql) ?: throw Exception("Failed to prepare sql statement")
        params.forEachIndexed { index, param -> param.bind((index + 1), stmt) }

        return stmt
    }
}
