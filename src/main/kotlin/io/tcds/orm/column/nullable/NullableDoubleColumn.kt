package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.nullable.NullableDoubleParam

class NullableDoubleColumn<Entity>(
    name: String,
    value: (Entity) -> Double?,
) : Column<Entity, Double?>(name, value) {
    override fun columnType(): String = "DOUBLE NULL"
    override fun toParam(value: Double?): Param<Double?> = NullableDoubleParam(this.name, value)
}
