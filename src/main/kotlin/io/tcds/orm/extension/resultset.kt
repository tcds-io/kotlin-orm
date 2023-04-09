package io.tcds.orm.extension

import com.fasterxml.jackson.module.kotlin.readValue
import io.tcds.orm.Column
import io.tcds.orm.JdbcOrmResultSet
import io.tcds.orm.OrmResultSet
import io.tcds.orm.column.JsonColumn

inline fun <reified T : Enum<T>> JdbcOrmResultSet.get(column: Column<*, T>): T = enumValueOf(rs.getString(column.name))
inline fun <reified T : Any> JdbcOrmResultSet.get(column: Column<*, T>): T {
    return JsonColumn.mapper.readValue(rs.getString(column.name))
}

inline fun <reified T : Enum<T>> JdbcOrmResultSet.nullable(column: Column<*, T?>): T? {
    return nullableValue(rs.getString(column.name))?.let { enumValueOf<T>(it) }
}

inline fun <reified T> JdbcOrmResultSet.nullable(column: Column<*, T?>): T? {
    return nullableValue(rs.getString(column.name))?.let { JsonColumn.mapper.readValue(rs.getString(column.name)) }
}

suspend fun <E> Sequence<OrmResultSet>.map(block: suspend (OrmResultSet) -> E): Sequence<E> {
    val items = mutableListOf<E>()
    forEach { items.add(block(it)) }

    return sequence { yieldAll(items) }
}
