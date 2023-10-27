package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement

data class EnumParam<T : Enum<*>>(
    override val name: String,
    override val value: T,
) : Param<T> {
    override fun bind(index: Int, stmt: PreparedStatement) = stmt.setString(index, value.name)
}
