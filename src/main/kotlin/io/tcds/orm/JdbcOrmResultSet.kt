package io.tcds.orm

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.sql.ResultSet as JdbcResultSet

class JdbcOrmResultSet(val rs: JdbcResultSet) : OrmResultSet {
    fun <T> nullableValue(value: T): T? = if (rs.wasNull()) null else value

    override fun get(column: Column<*, String>): String = rs.getString(column.name)
    override fun get(column: Column<*, Int>): Int = rs.getInt(column.name)
    override fun get(column: Column<*, Long>): Long = rs.getLong(column.name)
    override fun get(column: Column<*, Float>): Float = rs.getFloat(column.name)
    override fun get(column: Column<*, Double>): Double = rs.getDouble(column.name)
    override fun get(column: Column<*, Boolean>): Boolean = rs.getBoolean(column.name)
    override fun get(column: Column<*, LocalDateTime>): LocalDateTime = rs.getTimestamp(column.name)!!.time.let {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
    }

    override fun nullable(column: Column<*, String?>): String? = nullableValue(rs.getString(column.name))
    override fun nullable(column: Column<*, Int?>): Int? = nullableValue(rs.getInt(column.name))
    override fun nullable(column: Column<*, Long?>): Long? = nullableValue(rs.getLong(column.name))
    override fun nullable(column: Column<*, Float?>): Float? = nullableValue(rs.getFloat(column.name))
    override fun nullable(column: Column<*, Double?>): Double? = nullableValue(rs.getDouble(column.name))
    override fun nullable(column: Column<*, LocalDateTime?>): LocalDateTime? = rs.getTimestamp(column.name)?.time?.let {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
    }
}
