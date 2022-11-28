package io.tcds.orm.driver

import io.tcds.orm.column.*

interface ColumnBuilder {
    fun boolean(column: BooleanColumn<*>): String
    fun datetime(column: DateTimeColumn<*>): String
    fun decimal(column: DecimalColumn<*>, length: Int, scale: Int): String
    fun double(column: DoubleColumn<*>, length: Int, scale: Int): String
    fun enum(column: EnumColumn<*, *>): String
    fun float(column: FloatColumn<*>, length: Int, scale: Int): String
    fun integer(column: IntegerColumn<*>): String
    fun long(column: LongColumn<*>): String
    fun varchar(column: StringColumn<*>, length: Int): String
}
