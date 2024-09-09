package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.nullable.NullableLongParam

class NullableLongColumn<Entity>(name: String, value: (Entity) -> Long?) : Column<Entity, Long?>(name, value) {
    override fun columnType(): String = "LONG NULL"
    override fun valueParam(value: Long?): Param<Long?> = NullableLongParam(this.name, value)
    override fun entryParam(entry: Entity): Param<Long?> = NullableLongParam(this.name, valueOf(entry))
}
