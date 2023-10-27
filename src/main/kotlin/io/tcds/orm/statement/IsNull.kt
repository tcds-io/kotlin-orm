package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Condition
import io.tcds.orm.param.ColumnParam

data class IsNull<E>(val column: Column<E, *>) : Condition {
    override fun toStmt(): String = "${column.name} IS NULL"
    override fun toSql(): String = toStmt()
    override fun params(): List<ColumnParam<E, *>> = emptyList()
}
