package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Condition
import io.tcds.orm.param.ColumnParam

data class Between<E, T>(val column: Column<E, T>, private val first: T, private val last: T) : Condition {
    override fun toStmt(): String = "${column.name} BETWEEN ? AND ?"
    override fun toSql(): String = "${column.name} BETWEEN `$first` AND `$last`"
    override fun params(): List<ColumnParam<E, T>> = listOf(ColumnParam(column, first), ColumnParam(column, last))
}
