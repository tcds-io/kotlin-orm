package io.tcds.orm.extension

import io.tcds.orm.Column
import io.tcds.orm.statement.*

infix fun <T> Column<*, T>.equalsTo(value: T): Condition = EqualsTo(this, value)
infix fun <T> Column<*, T>.like(value: T): Condition = Like(this, value)
fun <T> Column<*, T>.isNull(): Condition = IsNull(this)
fun <T> Column<*, T>.isNotNull(): Condition = IsNotNull(this)
fun <T> Column<*, T>.isBetween(first: T, last: T): Condition = Between(this, first, last)
