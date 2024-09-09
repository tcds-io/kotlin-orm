package io.tcds.orm

import io.tcds.orm.statement.Order
import java.sql.PreparedStatement

abstract class Column<Entry, Type>(val name: String, val valueOf: (Entry) -> Type) {
    abstract fun columnType(): String
    abstract fun valueParam(value: Type): Param<Type>
    abstract fun entryParam(entry: Entry): Param<Type>
    override fun hashCode() = listOf(name, valueOf).hashCode()
    override fun equals(other: Any?): Boolean = other is Column<*, *> && other.hashCode() == hashCode()

    fun bind(stmt: PreparedStatement, index: Int, value: Type) = valueParam(value).bind(stmt, index)
    fun desc() = Pair(this, Order.DESC)
    fun asc() = Pair(this, Order.ASC)

    fun describe() = name to columnType()
}
