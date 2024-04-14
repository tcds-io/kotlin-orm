package io.tcds.orm.extension

import java.time.LocalDate
import java.time.LocalDateTime
import java.sql.ResultSet as JdbcResultSet

fun JdbcResultSet.getLocalDate(name: String): LocalDate = getDate(name).toLocalDate()
fun JdbcResultSet.getLocalDateTime(name: String): LocalDateTime = getTimestamp(name)!!.toLocalDateTime()
