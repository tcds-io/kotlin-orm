package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Param

class Between<E, T>(override val column: Column<E, T>, private val first: T, private val last: T) : Clause {
    override fun toString(): String = "${column.name} BETWEEN ? AND ?"
    override fun params(): List<Param<E, T>> = listOf(Param(column, first), Param(column, last))
}
