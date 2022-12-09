package io.tcds.orm.extension

import io.tcds.orm.Column
import io.tcds.orm.Condition
import io.tcds.orm.statement.*

fun <T> Column<*, T>.isBetween(first: T, last: T): Condition = Between(this, first, last)
infix fun <T> Column<*, T>.differentOf(value: T): Condition = DifferentOf(this, value)
infix fun <T> Column<*, T>.equalsTo(value: T): Condition = EqualsTo(this, value)
infix fun <T> Column<*, T>.greaterThen(value: T): Condition = GreaterThen(this, value)
infix fun <T> Column<*, T>.graterThenOrEqualsTo(value: T): Condition = GreaterThenOrEqualsTo(this, value)
fun <T> Column<*, T>.isNotNull(): Condition = IsNotNull(this)
fun <T> Column<*, T>.isNull(): Condition = IsNull(this)
infix fun <T> Column<*, T>.like(value: T): Condition = Like(this, value)
infix fun <T> Column<*, T>.smallerThen(value: T): Condition = SmallerThen(this, value)
infix fun <T> Column<*, T>.smallerThenOrEqualsTo(value: T): Condition = SmallerThenOrEqualsTo(this, value)
