package br.com.thiagocordeiro.orm

import br.com.thiagocordeiro.orm.type.EnumColumn
import br.com.thiagocordeiro.orm.type.IntegerColumn
import br.com.thiagocordeiro.orm.type.StringColumn
import java.sql.ResultSet

abstract class Table<E>(open val name: String) {
    private val columns = mutableListOf<Column<E>>()

    fun integer(name: String) = registerColumn(IntegerColumn(name = name))
    fun float(name: String) = registerColumn(StringColumn(name = name))
    fun decimal(name: String) = registerColumn(StringColumn(name = name))
    fun char(name: String) = registerColumn(StringColumn(name = name))
    fun varchar(name: String) = registerColumn(StringColumn(name = name))
    fun text(name: String) = registerColumn(StringColumn(name = name))

    fun bool(name: String) = registerColumn(StringColumn(name = name))
    fun datetime(name: String) = registerColumn(StringColumn(name = name))

    fun <T> enum(name: String) = registerColumn(EnumColumn<E, T>(name = name))

    fun insertColumns() = columns.joinToString(",")
    fun insertParams() = columns.joinToString(",") { ":$it" }

    abstract fun toDatabaseRow(entity: E): Map<Column<*>, Any>
    abstract fun toEntity(record: ResultSet): E

    protected fun registerColumn(column: Column<E>): Column<E> {
        columns.add(column)

        return column
    }
}
