package io.tcds.orm.driver

import io.tcds.orm.Column
import io.tcds.orm.OrmResultSet
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.sql.ResultSet as JdbcResultSet

class JdbcOrmResultSet(val jdbcResultSet: JdbcResultSet) : OrmResultSet {
    @Suppress("UNCHECKED_CAST")
    override fun <T> value(columnName: String, clazz: Class<T>): T? {
        return when (clazz) {
            String::class.java -> jdbcResultSet.getString(columnName) as T
            Int::class.java -> jdbcResultSet.getInt(columnName) as T
            Long::class.java -> jdbcResultSet.getLong(columnName) as T
            Float::class.java -> jdbcResultSet.getFloat(columnName) as T
            Double::class.java -> jdbcResultSet.getDouble(columnName) as T
            Boolean::class.java -> jdbcResultSet.getBoolean(columnName) as T
            LocalDate::class.java -> getLocalDate(columnName) as T
            LocalDateTime::class.java -> getLocalDateTime(columnName) as T
            else -> throw Exception("")
        }
    }

    override fun get(column: Column<*, String>): String = jdbcResultSet.getString(column.name)
    override fun get(column: Column<*, Int>): Int = jdbcResultSet.getInt(column.name)
    override fun get(column: Column<*, Long>): Long = jdbcResultSet.getLong(column.name)
    override fun get(column: Column<*, Float>): Float = jdbcResultSet.getFloat(column.name)
    override fun get(column: Column<*, Double>): Double = jdbcResultSet.getDouble(column.name)
    override fun get(column: Column<*, Boolean>): Boolean = jdbcResultSet.getBoolean(column.name)
    override fun get(column: Column<*, LocalDate>): LocalDate = getLocalDate(column.name)
    override fun get(column: Column<*, LocalDateTime>): LocalDateTime = getLocalDateTime(column.name)

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

    private fun getLocalDate(name: String): LocalDate = jdbcResultSet.getDate(name).toLocalDate()
    private fun getLocalDateTime(name: String): LocalDateTime = jdbcResultSet.getTimestamp(name)!!.time.let {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
    }
}
