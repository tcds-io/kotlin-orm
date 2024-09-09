package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.nullable.NullableFloatParam

class NullableFloatColumn<Entity>(name: String, value: (Entity) -> Float?) : Column<Entity, Float?>(name, value) {
    override fun columnType(): String = "FLOAT NULL"
    override fun valueParam(value: Float?): Param<Float?> = NullableFloatParam(this.name, value)
    override fun entryParam(entry: Entity): Param<Float?> = NullableFloatParam(this.name, valueOf(entry))
}
