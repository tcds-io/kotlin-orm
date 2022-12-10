package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Condition
import io.tcds.orm.Param

data class GreaterThenOrEqualsTo<E, T>(val column: Column<E, T>, private val value: T) : Condition {
    override fun toSql(): String = "${column.name} >= ?"
    override fun params(): List<Param<E, T>> = listOf(Param(column, value))
}
