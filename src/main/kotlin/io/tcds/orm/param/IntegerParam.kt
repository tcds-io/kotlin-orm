package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement

data class IntegerParam(
    override val name: String,
    override val value: Int,
) : Param<Int> {
    override fun bind(index: Int, stmt: PreparedStatement) = stmt.setInt(index, value)
}
