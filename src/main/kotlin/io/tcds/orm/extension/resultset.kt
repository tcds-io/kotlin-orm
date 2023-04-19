package io.tcds.orm.extension

import com.fasterxml.jackson.module.kotlin.readValue
import io.tcds.orm.Column
import io.tcds.orm.column.JsonColumn
import io.tcds.orm.driver.JdbcOrmResultSet

inline fun <reified T : Enum<T>> JdbcOrmResultSet.get(column: Column<*, T>): T {
    return enumValueOf(rs.getString(column.name))
}

inline fun <reified T : Enum<T>> JdbcOrmResultSet.nullable(column: Column<*, T?>): T? {
    return nullableValue(rs.getString(column.name))?.let { enumValueOf<T>(it) }
}

inline fun <reified T : Any> JdbcOrmResultSet.get(column: Column<*, T>): T {
    return JsonColumn.mapper.readValue(rs.getString(column.name))
}

inline fun <reified T : Any> JdbcOrmResultSet.nullable(column: Column<*, T?>): T? {
    return nullableValue(rs.getString(column.name))?.let { JsonColumn.mapper.readValue(rs.getString(column.name)) }
}
