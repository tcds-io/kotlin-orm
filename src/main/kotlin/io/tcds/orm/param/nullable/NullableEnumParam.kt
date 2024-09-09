package io.tcds.orm.param.nullable

import io.tcds.orm.Param
import java.sql.PreparedStatement
import java.sql.Types

data class NullableEnumParam<T : Enum<*>>(
    override val name: String,
    override val value: T?,
) : Param<T?> {
    override fun plain(): Any? = value?.name
    override fun bind(stmt: PreparedStatement, index: Int) = when (value) {
        null -> stmt.setNull(index, Types.VARCHAR)
        else -> stmt.setString(index, value.name)
    }
}
