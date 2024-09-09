package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Condition
import io.tcds.orm.Param

data class NotIn<E, T>(val column: Column<E, T>, private val values: List<T>) : Condition {
    override fun toStmt(): String = "${column.name} NOT IN (${values.joinToString(",") { "?" }})"
    override fun toSql(): String = "${column.name} NOT IN (${values.joinToString(", ") { "`$it`" }})"
    override fun params(): List<Param<T>> = values.map { column.valueParam(it) }
}
