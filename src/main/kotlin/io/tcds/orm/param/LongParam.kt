package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement

data class LongParam(
    override val name: String,
    override val value: Long,
) : Param<Long> {
    override fun bind(index: Int, stmt: PreparedStatement) = stmt.setLong(index, value)
}
