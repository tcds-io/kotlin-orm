package io.tcds.orm.extension

import io.tcds.orm.Column
import io.tcds.orm.OrmResultSet
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

inline fun <reified T : Enum<T>> OrmResultSet.get(column: Column<*, T?>): T? {
    return nullableValue(rs.getString(column.name))?.let { enumValueOf<T>(it) }
}

fun OrmResultSet.get(column: Column<*, String?>): String? = rs.getString(column.name)
fun OrmResultSet.get(column: Column<*, Int?>): Int? = nullableValue(rs.getInt(column.name))
fun OrmResultSet.get(column: Column<*, Long?>): Long? = nullableValue(rs.getLong(column.name))
fun OrmResultSet.get(column: Column<*, Float?>): Float? = nullableValue(rs.getFloat(column.name))
fun OrmResultSet.get(column: Column<*, Double?>): Double? = nullableValue(rs.getDouble(column.name))
fun OrmResultSet.get(column: Column<*, Boolean?>): Boolean? = nullableValue(rs.getBoolean(column.name))
fun OrmResultSet.get(column: Column<*, LocalDateTime?>): LocalDateTime? = rs.getTimestamp(column.name)?.time?.let {
    LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
}
