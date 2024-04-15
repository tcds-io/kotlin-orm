package io.tcds.orm.extension

import java.time.*
import java.util.Date
import java.sql.ResultSet as JdbcResultSet

private val utc = ZoneId.of("UTC")

fun JdbcResultSet.getInstant(name: String): Instant? = getTimestamp(name)?.toInstant()

// LocalDateTime <> Instant
fun Instant.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(this, utc)
fun LocalDateTime.toInstant(): Instant = atZone(utc).toInstant()

// LocalDate <> Instant
fun LocalDate.toInstant(): Instant = atStartOfDay().atZone(utc).toInstant()
fun Instant.toLocalDate(): LocalDate = LocalDate.ofInstant(this, utc)

// Date <> Instant
fun Instant.toDate(): Date = Date.from(this)

// ZonedDateTime <> Instant
fun Instant.toZonedDateTime(): ZonedDateTime = ZonedDateTime.ofInstant(this, utc)
