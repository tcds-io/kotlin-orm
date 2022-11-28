package io.tcds.orm.driver.mysql

import io.tcds.orm.column.*
import io.tcds.orm.driver.ColumnBuilder

class MysqlColumnBuilder : ColumnBuilder {
    override fun boolean(column: BooleanColumn<*>): String {
        return "`${column.name}` TINYINT(1)"
    }

    override fun datetime(column: DateTimeColumn<*>): String {
        return "`${column.name}` DATETIME(6)"
    }

    override fun decimal(column: DecimalColumn<*>, length: Int, scale: Int): String {
        return "`${column.name}` DECIMAL($length, $scale)"
    }

    override fun double(column: DoubleColumn<*>, length: Int, scale: Int): String {
        return "`${column.name}` DOUBLE PRECISION($length, $scale)"
    }

    override fun enum(column: EnumColumn<*, *>): String {
        return "`${column.name}` VARCHAR(255)"
    }

    override fun float(column: FloatColumn<*>, length: Int, scale: Int): String {
        return "`${column.name}` FLOAT($length, $scale)"
    }

    override fun integer(column: IntegerColumn<*>): String {
        return "`${column.name}` INTEGER"
    }

    override fun long(column: LongColumn<*>): String {
        return "`${column.name}` BIGINT"
    }

    override fun varchar(column: StringColumn<*>, length: Int): String {
        return "`${column.name}` VARCHAR($length)"
    }
}


//                 `amount_value`     DECIMAL(27, 12) NOT NULL,
