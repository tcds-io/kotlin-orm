package io.tcds.orm.extension

import com.fasterxml.jackson.module.kotlin.readValue
import io.tcds.orm.Column
import io.tcds.orm.OrmResultSet
import io.tcds.orm.column.JsonColumn
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

inline fun <reified T : Enum<T>> OrmResultSet.get(column: Column<*, T>): T = enumValueOf(rs.getString(column.name))
inline fun <reified T> OrmResultSet.get(column: Column<*, T>): T {
    return JsonColumn.mapper.readValue(rs.getString(column.name))
}

fun OrmResultSet.get(column: Column<*, String>): String = rs.getString(column.name)
fun OrmResultSet.get(column: Column<*, Int>): Int = rs.getInt(column.name)
fun OrmResultSet.get(column: Column<*, Long>): Long = rs.getLong(column.name)
fun OrmResultSet.get(column: Column<*, Float>): Float = rs.getFloat(column.name)
fun OrmResultSet.get(column: Column<*, Double>): Double = rs.getDouble(column.name)
fun OrmResultSet.get(column: Column<*, Boolean>): Boolean = rs.getBoolean(column.name)
fun OrmResultSet.get(column: Column<*, LocalDateTime>): LocalDateTime = rs.getTimestamp(column.name)!!.time.let {
    LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
}

inline fun <reified T : Enum<T>> OrmResultSet.nullable(column: Column<*, T?>): T? {
    return nullableValue(rs.getString(column.name))?.let { enumValueOf<T>(it) }
}

inline fun <reified T> OrmResultSet.nullable(column: Column<*, T?>): T? {
    return nullableValue(rs.getString(column.name))?.let { JsonColumn.mapper.readValue(rs.getString(column.name)) }
}

fun OrmResultSet.nullable(column: Column<*, String?>): String? = rs.getString(column.name)
fun OrmResultSet.nullable(column: Column<*, Int?>): Int? = nullableValue(rs.getInt(column.name))
fun OrmResultSet.nullable(column: Column<*, Long?>): Long? = nullableValue(rs.getLong(column.name))
fun OrmResultSet.nullable(column: Column<*, Float?>): Float? = nullableValue(rs.getFloat(column.name))
fun OrmResultSet.nullable(column: Column<*, Double?>): Double? = nullableValue(rs.getDouble(column.name))
fun OrmResultSet.nullable(column: Column<*, LocalDateTime?>): LocalDateTime? = rs.getTimestamp(column.name)?.time?.let {
    LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
}
