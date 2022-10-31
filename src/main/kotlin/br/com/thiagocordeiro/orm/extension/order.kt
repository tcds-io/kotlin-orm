package br.com.thiagocordeiro.orm.extension

import br.com.thiagocordeiro.orm.Column
import br.com.thiagocordeiro.orm.clauses.Order

fun Map<Column<*>, Order>.toOrderByStatement(): String {
    return when (isEmpty()) {
        true -> ""
        false -> "ORDER BY " + map { "${it.key.name} ${it.value.name}" }.toList().joinToString(", ")
    }
}
