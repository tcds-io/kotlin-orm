package io.tcds.orm.extension

import io.tcds.orm.Column
import io.tcds.orm.Condition
import io.tcds.orm.Param
import io.tcds.orm.Table
import io.tcds.orm.column.*
import io.tcds.orm.column.nullable.*
import io.tcds.orm.statement.*
import java.time.Instant

fun <T> Column<*, T>.isBetween(first: T, last: T): Condition = Between(this, first, last)
infix fun <T> Column<*, T>.differentOf(value: T): Condition = DifferentOf(this, value)
infix fun <T> Column<*, T>.equalsTo(value: T): Condition = EqualsTo(this, value)
infix fun <T> Column<*, T>.greaterThen(value: T): Condition = GreaterThen(this, value)
infix fun <T> Column<*, T>.graterThenOrEqualsTo(value: T): Condition = GreaterThenOrEqualsTo(this, value)
infix fun <T> Column<*, T>.valueIn(values: List<T>): Condition = In(this, values)
infix fun <T> Column<*, T>.valueNotIn(values: List<T>): Condition = NotIn(this, values)
fun <T> Column<*, T>.isNotNull(): Condition = IsNotNull(this)
fun <T> Column<*, T>.isNull(): Condition = IsNull(this)
infix fun <T> Column<*, T>.like(value: T): Condition = Like(this, value)
infix fun <T> Column<*, T>.smallerThen(value: T): Condition = SmallerThen(this, value)
infix fun <T> Column<*, T>.smallerThenOrEqualsTo(value: T): Condition = SmallerThenOrEqualsTo(this, value)

fun <E> Table<E>.varchar(name: String, value: (E) -> String) = column(StringColumn(name = name, value = value))
fun <E> Table<E>.varcharNullable(name: String, value: (E) -> String?) = column(NullableStringColumn(name = name, value = value))

fun <E> Table<E>.integer(name: String, value: (E) -> Int) = column(IntegerColumn(name = name, value = value))
fun <E> Table<E>.integerNullable(name: String, value: (E) -> Int?) = column(NullableIntegerColumn(name = name, value = value))

fun <E> Table<E>.long(name: String, value: (E) -> Long) = column(LongColumn(name = name, value = value))
fun <E> Table<E>.longNullable(name: String, value: (E) -> Long?) = column(NullableLongColumn(name = name, value = value))

fun <E> Table<E>.float(name: String, value: (E) -> Float) = column(FloatColumn(name = name, value = value))
fun <E> Table<E>.floatNullable(name: String, value: (E) -> Float?) = column(NullableFloatColumn(name = name, value = value))

fun <E> Table<E>.double(name: String, value: (E) -> Double) = column(DoubleColumn(name = name, value = value))
fun <E> Table<E>.doubleNullable(name: String, value: (E) -> Double?) = column(NullableDoubleColumn(name = name, value = value))

fun <E> Table<E>.bool(name: String, value: (E) -> Boolean) = column(BooleanColumn(name = name, value = value))

fun <E> Table<E>.date(name: String, value: (E) -> Instant) = column(TimestampColumn(name = name, value = value))
fun <E> Table<E>.datetime(name: String, value: (E) -> Instant) = column(TimestampColumn(name = name, value = value))
fun <E> Table<E>.dateNullable(name: String, value: (E) -> Instant?) = column(NullableTimestampColumn(name = name, value = value))
fun <E> Table<E>.datetimeNullable(name: String, value: (E) -> Instant?) = column(NullableTimestampColumn(name = name, value = value))

fun <E, T : Enum<*>> Table<E>.enum(name: String, value: (E) -> T) = column(EnumColumn(name = name, value = value))
fun <E, T> Table<E>.json(name: String, value: (E) -> T) = column(JsonColumn(name = name, value = value))

fun <E> Table<E>.params(entry: E): List<Param<*>> = columns.map { it.toValueParam(entry = entry) }
fun <E, T> Table<E>.column(column: Column<E, T>): Column<E, T> = columns.add(column).let { column }
