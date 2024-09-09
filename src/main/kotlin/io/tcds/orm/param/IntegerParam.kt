package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement

data class IntegerParam(
    override val name: String,
    override val value: Int,
) : Param<Int> {
    override fun plain(): Int = value
    override fun bind(stmt: PreparedStatement, index: Int) = stmt.setInt(index, value)
}
