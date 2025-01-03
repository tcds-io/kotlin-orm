package io.tcds.orm.statement

import io.tcds.orm.Condition
import io.tcds.orm.Param
import io.tcds.orm.column.DatetimeColumn
import io.tcds.orm.extension.*
import io.tcds.orm.param.InstantParam
import java.time.Instant
import java.time.LocalDateTime

data class Statement(val conditions: MutableList<Pair<Operator, Condition>>) {
    companion object {
        fun <E> deletedAt() = DatetimeColumn<E>("deleted_at") { Instant.now() }
    }

    fun toStmt() = conditions.toStmt()
    fun toSql() = conditions.toSql()

    fun add(condition: Pair<Operator, Condition>): Statement {
        when (conditions.isEmpty()) {
            true -> conditions.add(Pair(Operator.NONE, condition.second))
            else -> conditions.add(condition)
        }

        return this
    }

    fun params(): List<Param<*>> {
        val params = mutableListOf<Param<*>>()
        conditions.forEach { params.addAll(it.second.params()) }

        return params
    }

    fun getSoftDeleteQueryParams(): List<Param<*>> {
        val params = mutableListOf<Param<*>>(InstantParam("deleted_at", LocalDateTime.now().toInstant()))
        conditions.forEach { params.addAll(it.second.params()) }

        return params
    }

    fun <E> getSoftDeleteStatement(): Statement {
        val deletedAt = deletedAt<E>()
        if (conditions.isEmpty()) return where(deletedAt.isNull())

        return where(StatementGroup(conditions.removeWhere())) and deletedAt.isNull()
    }
}
