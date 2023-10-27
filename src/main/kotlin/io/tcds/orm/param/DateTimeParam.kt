package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.time.LocalDateTime

data class DateTimeParam(
    override val name: String,
    override val value: LocalDateTime,
) : Param<LocalDateTime> {
    override fun bind(index: Int, stmt: PreparedStatement) = stmt.setTimestamp(index, Timestamp.valueOf(value))
}
