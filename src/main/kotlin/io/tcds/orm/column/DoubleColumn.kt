package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.DoubleParam

class DoubleColumn<Entity>(
    name: String,
    value: (Entity) -> Double,
) : Column<Entity, Double>(name, value) {
    override fun columnType(): String = "DOUBLE"
    override fun valueParam(value: Double): Param<Double> = DoubleParam(this.name, value)
    override fun entryParam(entry: Entity): Param<Double> = DoubleParam(this.name, valueOf(entry))
    override fun ddl(): String = "`$name` DECIMAL(10, 2) NOT NULL"
}
