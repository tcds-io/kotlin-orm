package io.tcds.orm.extension

import io.tcds.orm.Column
import io.tcds.orm.statement.Order

typealias ColumnOrder<E> = Pair<Column<E, *>, Order>
typealias OrderStatement<E> = List<ColumnOrder<E>>

fun <E> OrderStatement<E>.toOrderByStatement(): String {
    return when (isEmpty()) {
        true -> ""
        false -> "ORDER BY " + map { "${it.first.name} ${it.second.name}" }.toList().joinToString(", ")
    }
}

private fun <E> createOrderStatement(orderedColumn: ColumnOrder<E>): OrderStatement<E> = listOf(orderedColumn)
fun <E> orderBy(orderedColumn: ColumnOrder<E>): OrderStatement<E> = createOrderStatement(orderedColumn)
infix fun <E> OrderStatement<E>.orderBy(orderedColumn: ColumnOrder<E>): OrderStatement<E> = this + createOrderStatement(orderedColumn)
