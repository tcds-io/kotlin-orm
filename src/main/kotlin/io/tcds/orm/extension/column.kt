package io.tcds.orm.extension

import io.tcds.orm.Column
import io.tcds.orm.statement.*

infix fun <T> Column<*, T>.equalsTo(value: T): Clause = EqualsTo(this, value)
infix fun <T> Column<*, T>.like(value: T): Clause = Like(this, value)
fun <T> Column<*, T>.isNull(): Clause = IsNull(this)
fun <T> Column<*, T>.isNotNull(): Clause = IsNotNull(this)
fun <T> Column<*, T>.isBetween(first: T, last: T): Clause = Between(this, first, last)
