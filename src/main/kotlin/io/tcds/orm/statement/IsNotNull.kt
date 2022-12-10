package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Condition
import io.tcds.orm.Param

data class IsNotNull<E>(val column: Column<E, *>) : Condition {
    override fun toSql(): String = "${column.name} IS NOT NULL"
    override fun params(): List<Param<E, *>> = emptyList()
}
