package io.tcds.orm.param.nullable

import io.tcds.orm.Param
import java.sql.PreparedStatement
import java.sql.Types

data class NullableFloatParam(
    override val name: String,
    override val value: Float?,
) : Param<Float?> {
    override fun bind(stmt: PreparedStatement, index: Int) = when (value) {
        null -> stmt.setNull(index, Types.FLOAT)
        else -> stmt.setFloat(index, value)
    }
}
