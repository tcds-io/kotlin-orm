package io.tcds.orm.statement

import io.tcds.orm.Condition
import io.tcds.orm.Param
import io.tcds.orm.column.DateTimeColumn
import io.tcds.orm.extension.*
import java.time.LocalDateTime

data class Statement(val conditions: MutableList<Pair<Operator, Condition>>) {
    companion object {
        fun <E> deletedAt() = DateTimeColumn<E>("deleted_at") { LocalDateTime.now() }
    }

    fun toStmt() = conditions.toStmt()
    fun toSql() = conditions.toSql()

    fun add(condition: Pair<Operator, Condition>) = conditions.add(condition)

    fun params(): List<Param<*, *>> {
        val params = mutableListOf<Param<*, *>>()
        conditions.forEach { params.addAll(it.second.params()) }

        return params
    }

    fun <E> getSoftDeleteQueryParams(): List<Param<*, *>> {
        val deletedAt = deletedAt<E>()
        val params = mutableListOf<Param<*, *>>(Param(deletedAt, LocalDateTime.now()))
        conditions.forEach { params.addAll(it.second.params()) }

        return params
    }

    fun <E> getSoftDeleteStatement(): Statement {
        val deletedAt = deletedAt<E>()
        if (conditions.isEmpty()) return where(deletedAt.isNull())

        return where(StatementGroup(conditions.removeWhere())) and deletedAt.isNull()
    }
}
