package io.tcds.orm

import io.tcds.orm.column.*
import io.tcds.orm.column.nullable.*
import io.tcds.orm.driver.Connection
import java.time.LocalDateTime

abstract class Table<E>(
    override val connection: Connection,
    open val tableName: String,
    open val softDelete: Boolean = false,
) : Repository<E> {
    val columns = mutableListOf<Column<E, *>>()
    override val table: Table<E> get() = this

    fun integer(name: String, value: (E) -> Int) = column(IntegerColumn(name = name, value = value))
    fun long(name: String, value: (E) -> Long) = column(LongColumn(name = name, value = value))
    fun float(name: String, value: (E) -> Float) = column(FloatColumn(name = name, value = value))
    fun double(name: String, value: (E) -> Double) = column(DoubleColumn(name = name, value = value))
    fun varchar(name: String, value: (E) -> String) = column(StringColumn(name = name, value = value))
    fun nullable(name: String, value: (E) -> String) = column(StringColumn(name = name, value = value))
    fun bool(name: String, value: (E) -> Boolean) = column(BooleanColumn(name = name, value = value))
    fun datetime(name: String, value: (E) -> LocalDateTime) = column(DateTimeColumn(name = name, value = value))
    fun <T : Enum<*>> enum(name: String, value: (E) -> T) = column(EnumColumn(name = name, value = value))
    fun <T> json(name: String, value: (E) -> T) = column(JsonColumn(name = name, value = value))

    fun nullableInteger(name: String, value: (E) -> Int?) = column(NullableIntegerColumn(name = name, value = value))
    fun nullableLong(name: String, value: (E) -> Long?) = column(NullableLongColumn(name = name, value = value))
    fun nullableFloat(name: String, value: (E) -> Float?) = column(NullableFloatColumn(name = name, value = value))
    fun nullableDouble(name: String, value: (E) -> Double?) = column(NullableDoubleColumn(name = name, value = value))
    fun nullableVarchar(name: String, value: (E) -> String?) = column(NullableStringColumn(name = name, value = value))
    fun nullableDatetime(name: String, value: (E) -> LocalDateTime?) =
        column(NullableDateTimeColumn(name = name, value = value))

    fun <T : Enum<*>> nullableEnum(name: String, value: (E) -> T?): Column<E, T?> {
        return column(NullableEnumColumn(name = name, value = value))
    }

    fun <T> nullableJson(name: String, value: (E) -> T?) = column(NullableJsonColumn(name = name, value = value))

    fun params(entry: E): List<Param<E, *>> = columns.map { it.toValueParam(entry = entry) }

    protected fun <T> column(column: Column<E, T>): Column<E, T> = columns.add(column).let { column }

    abstract fun entry(row: OrmResultSet): E
}
