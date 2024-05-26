package io.tcds.orm.param.nullable

import io.tcds.orm.Param
import java.sql.PreparedStatement
import java.sql.Types

data class NullableDoubleParam(
    override val name: String,
    override val value: Double?,
) : Param<Double?> {
    override fun bind(stmt: PreparedStatement, index: Int) = when (value) {
        null -> stmt.setNull(index, Types.DOUBLE)
        else -> stmt.setDouble(index, value)
    }
}
