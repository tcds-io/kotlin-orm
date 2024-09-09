package io.tcds.orm

import java.time.Instant

typealias OrmResult<T> = Map<String, T>

class MapOrmResultSet(private val data: OrmResult<*>) : OrmResultSet {
    @Suppress("UNCHECKED_CAST")
    override fun <T> value(columnName: String, clazz: Class<T>): T? = data[columnName] as T

    override fun get(column: Column<*, String>): String = data[column.name]!! as String
    override fun get(column: Column<*, Int>): Int = data[column.name]!! as Int
    override fun get(column: Column<*, Long>): Long = data[column.name]!! as Long
    override fun get(column: Column<*, Float>): Float = data[column.name]!! as Float
    override fun get(column: Column<*, Double>): Double = data[column.name]!! as Double
    override fun get(column: Column<*, Boolean>): Boolean = data[column.name]!! as Boolean
    override fun get(column: Column<*, Instant>): Instant = when (val value = data[column.name]) {
        is String -> Instant.parse(value)
        is Instant -> value
        else -> throw IllegalArgumentException("Unsupported type ${value?.javaClass}")
    }

    override fun nullable(column: Column<*, String?>): String? = data[column.name] as String?
    override fun nullable(column: Column<*, Int?>): Int? = data[column.name] as Int?
    override fun nullable(column: Column<*, Long?>): Long? = data[column.name] as Long?
    override fun nullable(column: Column<*, Float?>): Float? = data[column.name] as Float?
    override fun nullable(column: Column<*, Double?>): Double? = data[column.name] as Double?
    override fun nullable(column: Column<*, Instant?>): Instant? = data[column.name] as Instant?
}
