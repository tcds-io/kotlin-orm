package io.tcds.orm

import io.tcds.orm.column.*
import java.time.LocalDateTime

abstract class Table<E>(open val table: String) {
    private val columns = mutableListOf<Column<E, *>>()

    fun integer(name: String, value: (E) -> Int?) = registerColumn(IntegerColumn(name = name, value = value))
    fun float(name: String, value: (E) -> Double?) = registerColumn(DoubleColumn(name = name, value = value))
    fun varchar(name: String, value: (E) -> String?) = registerColumn(StringColumn(name = name, value = value))
    fun bool(name: String, value: (E) -> Boolean?) = registerColumn(BooleanColumn(name = name, value = value))
    fun datetime(name: String, value: (E) -> LocalDateTime?) = registerColumn(DateTimeColumn(name = name, value = value))

    // fun decimal(name: String, value: (E) -> Unit) = registerColumn(StringColumn(name = name, value = value))
    // fun char(name: String, value: (E) -> Unit) = registerColumn(StringColumn(name = name, value = value))
    // fun text(name: String, value: (E) -> Unit) = registerColumn(StringColumn(name = name, value = value))


    fun params(entry: E): List<Param<E, *>> = columns.map { it.toValueParam(entry = entry) }

    protected fun <T> registerColumn(column: Column<E, T?>): Column<E, T?> = columns.add(column).let { column }
    abstract fun entity(row: OrmResultSet): E
}
