package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Condition
import io.tcds.orm.param.ColumnParam

data class DifferentOf<E, T>(val column: Column<E, T>, private val value: T) : Condition {
    override fun toStmt(): String = "${column.name} != ?"
    override fun toSql(): String = "${column.name} != `$value`"
    override fun params(): List<ColumnParam<E, T>> = listOf(ColumnParam(column, value))
}
