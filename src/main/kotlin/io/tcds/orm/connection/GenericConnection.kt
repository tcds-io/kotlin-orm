package io.tcds.orm.connection

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Param
import io.tcds.orm.driver.JdbcOrmResultSet
import org.slf4j.Logger
import java.sql.PreparedStatement
import java.sql.Statement
import java.sql.Connection as JdbcConnection

open class GenericConnection(
    private val readOnly: ResilientConnection,
    private val readWrite: ResilientConnection,
    private val logger: Logger? = null,
) : Connection {
    override val reader: JdbcConnection = readOnly.instance()
    override val writer: JdbcConnection = readWrite.instance()

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

    override fun read(sql: String, params: List<Param<*>>): Sequence<OrmResultSet> {
        val stmt = prepare(readOnly.instance(), sql, params)
        val result = stmt.executeQuery()
        if (logger !== null) ConnectionLogger.read(logger, sql, params)

        return sequence {
            while (result.next()) {
                yield(JdbcOrmResultSet(result))
            }
        }
    }

    override fun write(sql: String, params: List<Param<*>>): Statement {
        val stmt = prepare(readWrite.instance(), sql, params)
        if (logger !== null) ConnectionLogger.write(logger, sql, params)
        stmt.execute()

        return stmt
    }

    open fun close() {
        readOnly.instance().close()
        readWrite.instance().close()
    }

    private fun prepare(
        conn: JdbcConnection,
        sql: String,
        params: List<Param<*>> = emptyList(),
    ): PreparedStatement {
        val stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ?: throw Exception("Failed to prepare sql statement")
        params.forEachIndexed { index, param -> param.bind(stmt, (index + 1)) }

        return stmt
    }
}
