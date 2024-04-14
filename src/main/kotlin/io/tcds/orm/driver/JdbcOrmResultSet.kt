package io.tcds.orm.driver

import io.tcds.orm.Column
import io.tcds.orm.OrmResultSet
import io.tcds.orm.extension.getLocalDate
import io.tcds.orm.extension.getLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.sql.ResultSet as JdbcResultSet

@Suppress("MemberVisibilityCanBePrivate")
class JdbcOrmResultSet(val jdbcResultSet: JdbcResultSet) : OrmResultSet {
    @Suppress("UNCHECKED_CAST")
    override fun <T> value(columnName: String, clazz: Class<T>): T? {
        return when (clazz) {
            String::class.java -> jdbcResultSet.getString(columnName) as T
            Int::class.java -> jdbcResultSet.getInt(columnName) as T
            Date::class.java -> jdbcResultSet.getDate(columnName) as T
            Long::class.java -> jdbcResultSet.getLong(columnName) as T
            Float::class.java -> jdbcResultSet.getFloat(columnName) as T
            Double::class.java -> jdbcResultSet.getDouble(columnName) as T
            Boolean::class.java -> jdbcResultSet.getBoolean(columnName) as T
            LocalDate::class.java -> jdbcResultSet.getLocalDate(columnName) as T
            LocalDateTime::class.java -> jdbcResultSet.getLocalDateTime(columnName) as T
            else -> throw Exception("")
        }
    }

    override fun get(column: Column<*, String>): String = jdbcResultSet.getString(column.name)
    override fun get(column: Column<*, Int>): Int = jdbcResultSet.getInt(column.name)
    override fun get(column: Column<*, Date>): Date = jdbcResultSet.getDate(column.name)
    override fun get(column: Column<*, Long>): Long = jdbcResultSet.getLong(column.name)
    override fun get(column: Column<*, Float>): Float = jdbcResultSet.getFloat(column.name)
    override fun get(column: Column<*, Double>): Double = jdbcResultSet.getDouble(column.name)
    override fun get(column: Column<*, Boolean>): Boolean = jdbcResultSet.getBoolean(column.name)
    override fun get(column: Column<*, LocalDate>): LocalDate = jdbcResultSet.getLocalDate(column.name)
    override fun get(column: Column<*, LocalDateTime>): LocalDateTime = jdbcResultSet.getLocalDateTime(column.name)

    override fun nullable(column: Column<*, String?>): String? = nullable { jdbcResultSet.getString(column.name) }
    override fun nullable(column: Column<*, Int?>): Int? = nullable { jdbcResultSet.getInt(column.name) }
    override fun nullable(column: Column<*, Date?>): Date? = nullable { jdbcResultSet.getDate(column.name) }
    override fun nullable(column: Column<*, Long?>): Long? = nullable { jdbcResultSet.getLong(column.name) }
    override fun nullable(column: Column<*, Float?>): Float? = nullable { jdbcResultSet.getFloat(column.name) }
    override fun nullable(column: Column<*, Double?>): Double? = nullable { jdbcResultSet.getDouble(column.name) }
    override fun nullable(column: Column<*, LocalDate?>): LocalDate? = nullable { jdbcResultSet.getLocalDate(column.name) }
    override fun nullable(column: Column<*, LocalDateTime?>): LocalDateTime? = nullable { jdbcResultSet.getLocalDateTime(column.name) }

    private fun <T> nullable(getter: () -> T): T? = if (jdbcResultSet.wasNull()) null else getter()
}
