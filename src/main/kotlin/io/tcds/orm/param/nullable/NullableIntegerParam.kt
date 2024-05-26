package io.tcds.orm.param.nullable

import io.tcds.orm.Param
import java.sql.PreparedStatement
import java.sql.Types

data class NullableIntegerParam(
    override val name: String,
    override val value: Int?,
) : Param<Int?> {
    override fun bind(stmt: PreparedStatement, index: Int) = when (value) {
        null -> stmt.setNull(index, Types.INTEGER)
        else -> stmt.setInt(index, value)
    }
}
