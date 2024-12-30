package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.nullable.NullableDoubleParam

class NullableDoubleColumn<Entity>(name: String, value: (Entity) -> Double?) : Column<Entity, Double?>(name, value) {
    override fun columnType(): String = "DOUBLE NULL"
    override fun valueParam(value: Double?): Param<Double?> = NullableDoubleParam(this.name, value)
    override fun entryParam(entry: Entity): Param<Double?> = NullableDoubleParam(this.name, valueOf(entry))
    override fun ddl(): String = "`$name` DECIMAL(10, 2)"
}
