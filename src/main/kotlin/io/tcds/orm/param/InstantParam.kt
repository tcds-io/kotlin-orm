package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.time.Instant

data class InstantParam(
    override val name: String,
    override val value: Instant,
) : Param<Instant> {
    override fun bind(stmt: PreparedStatement, index: Int) = stmt.setTimestamp(index, Timestamp.from(value))
}
