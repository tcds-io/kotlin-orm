package io.tcds.orm.driver

import io.tcds.orm.Column
import io.tcds.orm.OrmResultSet
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.sql.ResultSet as JdbcResultSet

class JdbcOrmResultSet(val jdbcResultSet: JdbcResultSet) : OrmResultSet {
    override fun value(columnName: String): String? = jdbcResultSet.getString(columnName)

    override fun get(column: Column<*, String>): String = jdbcResultSet.getString(column.name)
    override fun get(column: Column<*, Int>): Int = jdbcResultSet.getInt(column.name)
    override fun get(column: Column<*, Long>): Long = jdbcResultSet.getLong(column.name)
    override fun get(column: Column<*, Float>): Float = jdbcResultSet.getFloat(column.name)
    override fun get(column: Column<*, Double>): Double = jdbcResultSet.getDouble(column.name)
    override fun get(column: Column<*, Boolean>): Boolean = jdbcResultSet.getBoolean(column.name)
    override fun get(column: Column<*, LocalDate>): LocalDate = jdbcResultSet.getDate(column.name).toLocalDate()
    override fun get(column: Column<*, LocalDateTime>): LocalDateTime {
        return jdbcResultSet.getTimestamp(column.name)!!.time.let {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
        }
    }

    override fun nullable(column: Column<*, String?>): String? = nullableValue(jdbcResultSet.getString(column.name))
    override fun nullable(column: Column<*, Int?>): Int? = nullableValue(jdbcResultSet.getInt(column.name))
    override fun nullable(column: Column<*, Long?>): Long? = nullableValue(jdbcResultSet.getLong(column.name))
    override fun nullable(column: Column<*, Float?>): Float? = nullableValue(jdbcResultSet.getFloat(column.name))
    override fun nullable(column: Column<*, Double?>): Double? = nullableValue(jdbcResultSet.getDouble(column.name))
    override fun nullable(column: Column<*, LocalDate?>): LocalDate? {
        return nullableValue(jdbcResultSet.getDate(column.name)?.toLocalDate())
    }

    override fun nullable(column: Column<*, LocalDateTime?>): LocalDateTime? {
        return jdbcResultSet.getTimestamp(column.name)?.time?.let {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
        }
    }

    private fun <T> nullableValue(value: T): T? = if (jdbcResultSet.wasNull()) null else value
}
