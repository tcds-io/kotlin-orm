package io.tcds.orm.extension

import io.tcds.orm.Column
import io.tcds.orm.statement.Order

typealias OrderStatement<E> = List<Pair<Column<E, *>, Order>>

fun <E> OrderStatement<E>.toOrderByStatement(): String {
    return when (isEmpty()) {
        true -> ""
        false -> "ORDER BY " + map { "${it.first.name} ${it.second.name}" }.toList().joinToString(", ")
    }
}
