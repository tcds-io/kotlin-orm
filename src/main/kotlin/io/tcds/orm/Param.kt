package io.tcds.orm

import java.sql.PreparedStatement

data class Param<E, T>(val column: Column<E, T>, val value: T) {
    fun bind(index: Int, stmt: PreparedStatement) = column.bind(stmt, index, value)
}
