package io.tcds.orm.param

import io.tcds.orm.Column
import io.tcds.orm.Param
import java.sql.PreparedStatement

data class ColumnParam<Entry, Type>(
    val column: Column<Entry, Type>,
    override val value: Type,
) : Param<Type> {
    override val name: String = column.name
    override fun bind(stmt: PreparedStatement, index: Int) = column.bind(stmt, index, value)
}
