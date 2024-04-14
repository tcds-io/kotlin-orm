package io.tcds.orm.extension

import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.sql.ResultSet as JdbcResultSet

private val utc = ZoneId.of("UTC")

fun Timestamp.toUtcLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.time), utc)

fun JdbcResultSet.getLocalDate(name: String): LocalDate = getDate(name).toLocalDate()
fun JdbcResultSet.getLocalDateTime(name: String): LocalDateTime? = getTimestamp(name)?.toUtcLocalDateTime()

fun Date.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), utc)
fun Date.toLocalDate(): LocalDate = LocalDate.ofInstant(Instant.ofEpochMilli(time), utc)
fun LocalDateTime.toDate(): Date = Date.from(atZone(utc).toInstant())
fun LocalDate.toDate(): Date = Date.from(atStartOfDay().atZone(utc).toInstant())
