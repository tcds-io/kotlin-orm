package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Condition
import io.tcds.orm.Param

data class In<E, T>(val column: Column<E, T>, private val values: List<T>) : Condition {
    override fun toStmt(): String = "${column.name} IN (${values.joinToString(",") { "?" }})"
    override fun toSql(): String = "${column.name} IN (${values.joinToString(", ") { "`$it`" }})"
    override fun params(): List<Param<E, T>> = values.map { Param(column, it) }
}
