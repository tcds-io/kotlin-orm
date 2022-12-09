package io.tcds.orm.driver

import org.slf4j.Logger
import java.sql.DriverManager
import java.sql.Connection as JdbcConnection

abstract class NestedTransactionConnection(
    jdbcReadUrl: String,
    jdbcReadWriteUrl: String,
    override val logger: Logger?,
) : Connection {
    companion object {
        private const val INITIAL_DEPTH = 0
    }

    final override val readOnly: JdbcConnection = DriverManager.getConnection(jdbcReadUrl)
    final override val readWrite: JdbcConnection = DriverManager.getConnection(jdbcReadWriteUrl)

    init {
        if (!readOnly.isValid(0)) throw Exception("Orm: Invalid ro connection")
        if (!readWrite.isValid(0)) throw Exception("Orm: Invalid rw connection")
    }

    private var txDepth = INITIAL_DEPTH

    override fun begin() = when (txDepth) {
        INITIAL_DEPTH -> super.begin()
        else -> execute("SAVEPOINT LEVEL${txDepth++}")
    }

    override fun commit() = decrementTxDepth().let {
        when (it) {
            INITIAL_DEPTH -> super.commit()
            else -> execute("RELEASE SAVEPOINT LEVEL${it}")
        }
    }

    override fun rollback() = decrementTxDepth().let {
        when (it) {
            INITIAL_DEPTH -> super.rollback()
            else -> execute("ROLLBACK TO SAVEPOINT LEVEL${it}")
        }
    }

    private fun decrementTxDepth(): Int {
        txDepth--

        if (txDepth < INITIAL_DEPTH) {
            txDepth = INITIAL_DEPTH
            throw Exception("Transaction error: There are no transactions started")
        }

        return txDepth
    }
}
