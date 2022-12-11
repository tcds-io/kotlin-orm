package io.tcds.orm.driver

import org.slf4j.Logger
import java.sql.Connection as JdbcConnection

abstract class NestedTransactionConnection(
    final override val readOnly: JdbcConnection,
    final override val readWrite: JdbcConnection,
    override val logger: Logger?,
) : Connection {
    companion object {
        private const val INITIAL_DEPTH = 0
    }

    init {
        if (!readOnly.isValid(0)) throw Exception("Orm: Invalid ro connection")
        if (!readWrite.isValid(0)) throw Exception("Orm: Invalid rw connection")
    }

    private var transactionDepth = 0

    override fun begin() = when (transactionDepth) {
        INITIAL_DEPTH -> super.begin().apply { transactionDepth++ }
        else -> execute("SAVEPOINT LEVEL${transactionDepth++}")
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
        transactionDepth--

        if (transactionDepth < INITIAL_DEPTH) {
            transactionDepth = INITIAL_DEPTH
            throw Exception("Transaction error: There are no transactions started")
        }

        return transactionDepth
    }
}
