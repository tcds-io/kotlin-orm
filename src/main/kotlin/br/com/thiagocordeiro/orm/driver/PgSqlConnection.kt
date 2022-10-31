package br.com.thiagocordeiro.orm.driver

import br.com.thiagocordeiro.orm.Connection
import br.com.thiagocordeiro.orm.clauses.Clause
import java.sql.DriverManager
import java.sql.ResultSet

class PgSqlConnection(
    private val jdbcRwUrl: String,
    private val jdbcRoUrl: String,
) : Connection {
    private val readOnly = DriverManager.getConnection(jdbcRoUrl, "postgres", "postgres")
    private val readWrite = DriverManager.getConnection(jdbcRwUrl, "postgres", "postgres")

    init {
        if (!readOnly.isValid(0)) throw Exception("Orm: Invalid ro connection")
        if (!readWrite.isValid(0)) throw Exception("Orm: Invalid rw connection")
    }

    override fun select(sql: String, params: List<Clause>): Sequence<ResultSet> {
        val stmt = readOnly.prepareStatement(sql)
        // params.forEachIndexed { index, clause -> clause.bind(index, stmt) }

        val result = stmt.executeQuery()

        return sequence {
            while (result.next()) {
                yield(result)
            }
        }
    }

    override fun execute(sql: String, params: List<Clause>) {
        TODO("Not yet implemented")
    }
}
