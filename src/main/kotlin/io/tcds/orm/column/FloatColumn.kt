package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.FloatParam

class FloatColumn<Entity>(
    name: String,
    value: (Entity) -> Float,
) : Column<Entity, Float>(name, value) {
    override fun columnType(): String = "FLOAT"
    override fun valueParam(value: Float): Param<Float> = FloatParam(this.name, value)
    override fun entryParam(entry: Entity): Param<Float> = FloatParam(this.name, valueOf(entry))
    override fun ddl(): String = "`$name` FLOAT NOT NULL"
}
