package io.tcds.orm.connection

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Param
import io.tcds.orm.driver.JdbcOrmResultSet
import org.slf4j.Logger
import java.sql.PreparedStatement
import java.sql.Connection as JdbcConnection

open class GenericConnection(
    private val readOnly: JdbcConnection,
    private val readWrite: JdbcConnection,
    private val logger: Logger?,
) : Connection {
    override fun begin() = write("BEGIN")
    override fun commit() = write("COMMIT")
    override fun rollback() = write("ROLLBACK")

    override fun <T> transaction(block: () -> T): T {
        return try {
            begin()
            block().apply { commit() }
        } catch (e: Exception) {
            rollback().let { throw e }
        }
    }

    override fun read(sql: String, params: List<Param<*, *>>): Sequence<OrmResultSet> {
        val stmt = prepare(readOnly, sql, params)
        val result = stmt.executeQuery()
        if (logger !== null) ConnectionLogger.read(logger, sql, params)

        return sequence {
            while (result.next()) {
                yield(JdbcOrmResultSet(result))
            }
        }
    }

    override fun write(sql: String, params: List<Param<*, *>>): Boolean {
        val stmt = prepare(readWrite, sql, params)
        if (logger !== null) ConnectionLogger.write(logger, sql, params)

        return stmt.execute()
    }

    open fun close() {
        readOnly.close()
        readWrite.close()
    }

    private fun prepare(
        conn: JdbcConnection,
        sql: String,
        params: List<Param<*, *>> = emptyList(),
    ): PreparedStatement {
        val stmt = conn.prepareStatement(sql) ?: throw Exception("Failed to prepare sql statement")
        params.forEachIndexed { index, param -> param.bind((index + 1), stmt) }

        return stmt
    }
}
