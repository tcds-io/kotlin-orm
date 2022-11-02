package io.tcds.orm.extension

import io.tcds.orm.Param
import io.tcds.orm.statement.Condition

fun List<Condition>.toWhereStatement(): String {
    return when (isEmpty()) {
        true -> ""
        false -> "WHERE " + joinToString(" AND ") { it.toString() }
    }
}

fun List<Condition>.toParams(): List<Param<*, *>> {
    val params = mutableListOf<Param<*, *>>()

    forEach { params.addAll(it.params()) }

    return params
}

fun List<Param<*, *>>.columns(): String = joinToString(", ") { it.column.name }
fun List<Param<*, *>>.marks(): String = joinToString(", ") { "?" }
