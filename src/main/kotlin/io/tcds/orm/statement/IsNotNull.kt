package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Param

class IsNotNull<E>(override val column: Column<E, *>) : Clause {
    override fun toString(): String = "${column.name} IS NOT NULL"
    override fun params(): List<Param<E, *>> = emptyList()
}
