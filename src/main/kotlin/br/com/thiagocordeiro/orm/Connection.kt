package br.com.thiagocordeiro.orm

import br.com.thiagocordeiro.orm.clauses.Clause
import br.com.thiagocordeiro.orm.clauses.Order
import br.com.thiagocordeiro.orm.extension.toOrderByStatement
import br.com.thiagocordeiro.orm.extension.toWhereStatement
import java.sql.ResultSet

interface Connection {
    fun select(
        table: String,
        where: List<Clause>,
        order: Map<Column<*>, Order>,
        limit: Int? = null,
        offset: Int? = null,
    ): Sequence<ResultSet> {
        val sLimit = if (limit !== null) "LIMIT $limit" else ""
        val sOffset = if (offset !== null) "OFFSET $offset" else ""

        return select(
            "SELECT * FROM $table ${where.toWhereStatement()} ${order.toOrderByStatement()} $sLimit $sOffset",
            listOf(),
        )
    }

    fun select(sql: String, params: List<Clause>): Sequence<ResultSet>

    fun execute(sql: String, params: List<Clause>)
}
