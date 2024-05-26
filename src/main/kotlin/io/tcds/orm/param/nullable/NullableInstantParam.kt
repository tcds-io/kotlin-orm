package io.tcds.orm.param.nullable

import io.tcds.orm.Param
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.sql.Types
import java.time.Instant

data class NullableInstantParam(
    override val name: String,
    override val value: Instant?,
) : Param<Instant?> {
    override fun bind(stmt: PreparedStatement, index: Int) = when (value) {
        null -> stmt.setNull(index, Types.TIMESTAMP)
        else -> stmt.setTimestamp(index, Timestamp.from(value))
    }
}
