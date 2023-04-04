package io.tcds.orm.extension

import io.tcds.orm.Column
import io.tcds.orm.statement.Order

typealias OrderStatement<E> = Map<Column<E, *>, Order>

fun <E> Map<Column<E, *>, Order>.toOrderByStatement(): String {
    return when (isEmpty()) {
        true -> ""
        false -> "ORDER BY " + map { "${it.key.name} ${it.value.name}" }.toList().joinToString(", ")
    }
}
