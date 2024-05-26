package io.tcds.orm

import io.tcds.orm.param.ColumnParam
import io.tcds.orm.statement.Order
import java.sql.PreparedStatement

abstract class Column<Entry, Type>(val name: String, val valueOf: (Entry) -> Type) {
    abstract fun columnType(): String
    abstract fun toParam(value: Type): Param<Type>
    override fun hashCode() = listOf(name, valueOf).hashCode()
    override fun equals(other: Any?): Boolean = other is Column<*, *> && other.hashCode() == hashCode()

    fun bind(stmt: PreparedStatement, index: Int, value: Type) = toParam(value).bind(stmt, index)
    fun toColumnParam(entry: Entry): Param<Type> = ColumnParam(this, valueOf(entry))
    fun desc() = Pair(this, Order.DESC)
    fun asc() = Pair(this, Order.ASC)

    fun describe() = name to columnType()
}
