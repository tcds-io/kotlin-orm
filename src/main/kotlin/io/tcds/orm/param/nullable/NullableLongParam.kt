package io.tcds.orm.param.nullable

import io.tcds.orm.Param
import java.sql.PreparedStatement
import java.sql.Types

data class NullableLongParam(
    override val name: String,
    override val value: Long?,
) : Param<Long?> {
    override fun bind(stmt: PreparedStatement, index: Int) = when (value) {
        null -> stmt.setNull(index, Types.BIGINT)
        else -> stmt.setLong(index, value.toLong())
    }
}
