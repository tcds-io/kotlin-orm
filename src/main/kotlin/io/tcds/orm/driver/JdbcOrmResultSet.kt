package io.tcds.orm.driver

import io.tcds.orm.Column
import io.tcds.orm.OrmResultSet
import io.tcds.orm.extension.getInstant
import java.time.Instant
import java.sql.ResultSet as JdbcResultSet

@Suppress("MemberVisibilityCanBePrivate")
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
            Instant::class.java -> jdbcResultSet.getInstant(columnName) as T
            else -> throw Exception("")
        }
    }

    override fun get(column: Column<*, String>): String = jdbcResultSet.getString(column.name)
    override fun get(column: Column<*, Int>): Int = jdbcResultSet.getInt(column.name)
    override fun get(column: Column<*, Long>): Long = jdbcResultSet.getLong(column.name)
    override fun get(column: Column<*, Float>): Float = jdbcResultSet.getFloat(column.name)
    override fun get(column: Column<*, Double>): Double = jdbcResultSet.getDouble(column.name)
    override fun get(column: Column<*, Boolean>): Boolean = jdbcResultSet.getBoolean(column.name)
    override fun get(column: Column<*, Instant>): Instant = jdbcResultSet.getInstant(column.name)!!

    override fun nullable(column: Column<*, String?>): String? = nullable { jdbcResultSet.getString(column.name) }
    override fun nullable(column: Column<*, Int?>): Int? = nullable { jdbcResultSet.getInt(column.name) }
    override fun nullable(column: Column<*, Long?>): Long? = nullable { jdbcResultSet.getLong(column.name) }
    override fun nullable(column: Column<*, Float?>): Float? = nullable { jdbcResultSet.getFloat(column.name) }
    override fun nullable(column: Column<*, Double?>): Double? = nullable { jdbcResultSet.getDouble(column.name) }
    override fun nullable(column: Column<*, Instant?>): Instant? = nullable { jdbcResultSet.getInstant(column.name) }

    private fun <T> nullable(getter: () -> T?): T? = if (jdbcResultSet.wasNull()) null else getter()
}
