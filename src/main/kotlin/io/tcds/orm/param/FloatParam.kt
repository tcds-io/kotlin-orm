package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement

data class FloatParam(
    override val name: String,
    override val value: Float,
) : Param<Float> {
    override fun bind(index: Int, stmt: PreparedStatement) = stmt.setFloat(index, value)
}
