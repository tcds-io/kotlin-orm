package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.PreparedStatement

data class StringParam(
    override val name: String,
    override val value: String,
) : Param<String> {
    override fun plain(): String = value
    override fun bind(stmt: PreparedStatement, index: Int) = stmt.setString(index, value)
}
