package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement

data class LongParam(
    override val name: String,
    override val value: Long,
) : Param<Long> {
    override fun plain(): Long = value
    override fun bind(stmt: PreparedStatement, index: Int) = stmt.setLong(index, value)
}
