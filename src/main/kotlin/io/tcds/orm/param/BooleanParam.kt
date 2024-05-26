package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement

data class BooleanParam(
    override val name: String,
    override val value: Boolean,
) : Param<Boolean> {
    override fun bind(stmt: PreparedStatement, index: Int) = stmt.setBoolean(index, value)
}
