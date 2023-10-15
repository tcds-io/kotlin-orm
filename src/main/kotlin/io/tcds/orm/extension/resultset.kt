package io.tcds.orm.extension

import com.fasterxml.jackson.module.kotlin.readValue
import io.tcds.orm.Column
import io.tcds.orm.OrmException
import io.tcds.orm.OrmResultSet
import io.tcds.orm.column.JsonColumn

inline fun <reified T : Enum<T>> OrmResultSet.get(column: Column<*, T>): T = enumValueOf(value(column.name)!!)
inline fun <reified T : Any> OrmResultSet.get(column: Column<*, T>): T = column.name.let { json(it, value(it)!!) }

inline fun <reified T : Enum<T>> OrmResultSet.nullable(column: Column<*, T?>): T? {
    return value<String>(column.name)?.let { enumValueOf<T>(it) }
}

inline fun <reified T : Any> OrmResultSet.nullable(column: Column<*, T?>): T? {
    return value<String>(column.name)?.let { json(column.name, it) }
}

inline fun <reified T : Any> json(columnName: String, value: String): T {
    return try {
        JsonColumn.mapper.readValue(value)
    } catch (ex: Exception) {
        throw OrmException("Failed to parse json value of `$columnName`")
    }
}

inline fun <reified T> OrmResultSet.value(columnName: String): T? = value(columnName, T::class.java)
