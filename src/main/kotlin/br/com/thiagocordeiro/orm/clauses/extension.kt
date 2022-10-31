package br.com.thiagocordeiro.orm.clauses

import br.com.thiagocordeiro.orm.Column

infix fun <T> Column<T>.equalsTo(value: Any): Clause = EqualsTo(this, value)
infix fun <T> Column<T>.like(value: Any): Clause = Like(this, value)
fun <T> Column<T>.isNull(): Clause = IsNull(this)
fun <T> Column<T>.isNotNull(): Clause = IsNotNull(this)
fun <T> Column<T>.isBetween(first: T, last: T): Clause = Between(this, first, last)
