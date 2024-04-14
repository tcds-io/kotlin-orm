package io.tcds.orm.extension

import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.sql.ResultSet as JdbcResultSet

fun Timestamp.toUtcLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.time), ZoneId.of("UTC"))

fun JdbcResultSet.getLocalDate(name: String): LocalDate = getDate(name).toLocalDate()
fun JdbcResultSet.getLocalDateTime(name: String): LocalDateTime? = getTimestamp(name)?.toUtcLocalDateTime()
