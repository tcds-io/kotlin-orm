package io.tcds.orm

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.sql.ResultSet as JdbcResultSet

class OrmResultSet(private val rs: JdbcResultSet) {
    fun get(column: Column<*, String>): String = rs.getString(column.name)
    fun get(column: Column<*, Int>): Int = rs.getInt(column.name)
    fun get(column: Column<*, Double>): Double = rs.getDouble(column.name)
    fun get(column: Column<*, Boolean>): Boolean = rs.getBoolean(column.name)
    fun get(column: Column<*, LocalDateTime?>): LocalDateTime? {
        val timestamp = rs.getTimestamp(column.name)?.time ?: return null
        val instant = Instant.ofEpochMilli(timestamp)
        val zoneId = ZoneId.systemDefault()

        return LocalDateTime.ofInstant(instant, zoneId)
    }
}
