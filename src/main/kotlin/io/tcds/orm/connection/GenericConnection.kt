package io.tcds.orm.connection

import io.tcds.orm.JdbcOrmResultSet
import io.tcds.orm.OrmResultSet
import io.tcds.orm.Param
import org.slf4j.Logger
import java.sql.PreparedStatement
import java.sql.Connection as JdbcConnection

open class GenericConnection(
    private val readOnly: JdbcConnection,
    private val readWrite: JdbcConnection,
    private val logger: Logger?,
) : Connection {
    override suspend fun begin() = write("BEGIN")
    override suspend fun commit() = write("COMMIT")
    override suspend fun rollback() = write("ROLLBACK")

    override suspend fun <T> transaction(block: () -> T) {
        begin()

        runCatching { block() }
            .onSuccess { commit() }
            .onFailure { rollback().apply { throw it } }
    }

    override suspend fun read(sql: String, params: List<Param<*, *>>): Sequence<OrmResultSet> {
        val stmt = prepare(readOnly, sql, params)
        val result = stmt.executeQuery()
        if (logger !== null) ConnectionLogger.read(logger, sql, params)

        return sequence {
            while (result.next()) {
                yield(JdbcOrmResultSet(result))
            }
        }
    }

    override suspend fun write(sql: String, params: List<Param<*, *>>): Boolean {
        val stmt = prepare(readWrite, sql, params)
        if (logger !== null) ConnectionLogger.write(logger, sql, params)

        return stmt.execute()
    }

    open suspend fun close() {
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
