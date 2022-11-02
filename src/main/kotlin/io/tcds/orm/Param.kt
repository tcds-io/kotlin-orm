package io.tcds.orm

import java.sql.PreparedStatement

data class Param<Entry, Type>(val column: Column<Entry, Type>, val value: Type) {
    fun bind(index: Int, stmt: PreparedStatement) = column.bind(stmt, index, value)
}
