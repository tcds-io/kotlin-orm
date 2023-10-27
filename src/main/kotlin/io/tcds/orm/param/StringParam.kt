package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement

data class StringParam(
    override val name: String,
    override val value: String,
) : Param<String> {
    override fun bind(index: Int, stmt: PreparedStatement) = stmt.setString(index, value)
}
