package io.tcds.orm

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.sql.ResultSet as JdbcResultSet

class OrmResultSet(val rs: JdbcResultSet) {
    fun get(column: Column<*, String?>): String? = rs.getString(column.name)
    fun get(column: Column<*, Int?>): Int? = nullableValue(rs.getInt(column.name))
    fun get(column: Column<*, Double?>): Double? = nullableValue(rs.getDouble(column.name))
    fun get(column: Column<*, Boolean?>): Boolean? = nullableValue(rs.getBoolean(column.name))
    fun get(column: Column<*, LocalDateTime?>): LocalDateTime? {
        val timestamp = rs.getTimestamp(column.name)?.time ?: return null
        val instant = Instant.ofEpochMilli(timestamp)
        val zoneId = ZoneId.systemDefault()

        return LocalDateTime.ofInstant(instant, zoneId)
    }

    private fun <T> nullableValue(value: T): T? = if (rs.wasNull()) null else value
}
