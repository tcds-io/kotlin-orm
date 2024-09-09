package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement

data class FloatParam(
    override val name: String,
    override val value: Float,
) : Param<Float> {
    override fun plain(): Float = value
    override fun bind(stmt: PreparedStatement, index: Int) = stmt.setFloat(index, value)
}
