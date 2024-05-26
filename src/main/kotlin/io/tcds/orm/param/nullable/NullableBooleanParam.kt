package io.tcds.orm.param.nullable

import io.tcds.orm.Param
import java.sql.PreparedStatement
import java.sql.Types

data class NullableBooleanParam(
    override val name: String,
    override val value: Boolean?,
) : Param<Boolean?> {
    override fun bind(stmt: PreparedStatement, index: Int) = when (value) {
        null -> stmt.setNull(index, Types.BOOLEAN)
        else -> stmt.setBoolean(index, value)
    }
}
