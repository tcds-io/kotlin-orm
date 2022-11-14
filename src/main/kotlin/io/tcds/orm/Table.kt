package io.tcds.orm

import io.tcds.orm.column.*
import java.time.LocalDateTime

abstract class Table<E>(
    open val tableName: String,
    open val softDelete: Boolean = false,
) {
    private val columns = mutableListOf<Column<E, *>>()

    fun integer(name: String, value: (E) -> Int?) = column(IntegerColumn(name = name, value = value))
    fun long(name: String, value: (E) -> Long?) = column(LongColumn(name = name, value = value))
    fun float(name: String, value: (E) -> Float?) = column(FloatColumn(name = name, value = value))
    fun double(name: String, value: (E) -> Double?) = column(DoubleColumn(name = name, value = value))
    fun varchar(name: String, value: (E) -> String?) = column(StringColumn(name = name, value = value))
    fun bool(name: String, value: (E) -> Boolean?) = column(BooleanColumn(name = name, value = value))
    fun datetime(name: String, value: (E) -> LocalDateTime?) = column(DateTimeColumn(name = name, value = value))
    fun <T : Enum<*>> enum(name: String, value: (E) -> T?) = column(EnumColumn(name = name, value = value))

    fun params(entry: E): List<Param<E, *>> = columns.map { it.toValueParam(entry = entry) }

    protected fun <T> column(column: Column<E, T?>): Column<E, T?> = columns.add(column).let { column }

    abstract fun entry(row: OrmResultSet): E
}
