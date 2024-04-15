package io.tcds.orm.extension

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.sql.ResultSet as JdbcResultSet

private val utc = ZoneId.of("UTC")

fun JdbcResultSet.getInstant(name: String): Instant = getTimestamp(name).toInstant()

fun Instant.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(this, utc)
fun Instant.toLocalDate(): LocalDate = LocalDate.ofInstant(this, utc)
fun LocalDateTime.toInstant(): Instant = atZone(utc).toInstant()
fun LocalDate.toInstant(): Instant = atStartOfDay().atZone(utc).toInstant()
