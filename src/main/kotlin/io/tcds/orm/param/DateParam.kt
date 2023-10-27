package io.tcds.orm.param

import io.tcds.orm.Param
import java.sql.Date
import java.sql.PreparedStatement
import java.time.LocalDate

data class DateParam(
    override val name: String,
    override val value: LocalDate,
) : Param<LocalDate> {
    override fun bind(index: Int, stmt: PreparedStatement) = stmt.setDate(index, Date.valueOf(value))
}
