package io.tcds.orm

import java.sql.PreparedStatement

abstract class Column<Entry, Type>(val name: String, val valueOf: (Entry) -> Type) {
    abstract fun bind(stmt: PreparedStatement, index: Int, value: Type)

    fun toValueParam(entry: Entry): Param<Entry, Type> = Param(this, valueOf(entry))

    override fun hashCode() = listOf(name, valueOf).hashCode()
    override fun equals(other: Any?): Boolean = other is Column<*, *> && other.hashCode() == hashCode()
}
