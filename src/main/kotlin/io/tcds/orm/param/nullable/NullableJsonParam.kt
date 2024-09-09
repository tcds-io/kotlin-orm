package io.tcds.orm.param.nullable

import io.tcds.orm.Param
import io.tcds.orm.param.JsonParam
import java.sql.PreparedStatement
import java.sql.Types

data class NullableJsonParam<T>(
    override val name: String,
    override val value: T?,
) : Param<T?> {
    override fun plain(): String? = value?.let { JsonParam.mapper.writeValueAsString(it) }
    override fun bind(stmt: PreparedStatement, index: Int) = when (value) {
        null -> stmt.setNull(index, Types.VARCHAR)
        else -> stmt.setString(index, JsonParam.mapper.writeValueAsString(value))
    }
}
