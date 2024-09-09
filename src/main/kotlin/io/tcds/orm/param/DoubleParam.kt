package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement

data class DoubleParam(
    override val name: String,
    override val value: Double,
) : Param<Double> {
    override fun plain(): Double = value
    override fun bind(stmt: PreparedStatement, index: Int) = stmt.setDouble(index, value)
}
