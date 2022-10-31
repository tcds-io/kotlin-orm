package br.com.thiagocordeiro.orm.extension

import br.com.thiagocordeiro.orm.clauses.Clause

fun List<Clause>.toWhereStatement(): String {
    return when (isEmpty()) {
        true -> ""
        false -> "WHERE " + joinToString(" AND ") { it.toString() }
    }
}
