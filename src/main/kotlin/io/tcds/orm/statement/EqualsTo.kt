package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Param

class EqualsTo<E, T>(override val column: Column<E, T>, private val value: T) : Clause {
    override fun toString(): String = "${column.name} = ?"
    override fun params(): List<Param<E, T>> = listOf(Param(column, value))
}
