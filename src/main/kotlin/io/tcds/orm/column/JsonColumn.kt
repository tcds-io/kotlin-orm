package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.JsonParam

class JsonColumn<Entity, T>(
    name: String,
    value: (Entity) -> T,
) : Column<Entity, T>(name, value) {
    override fun columnType(): String = "JSON"
    override fun valueParam(value: T): Param<T> = JsonParam(this.name, value)
    override fun entryParam(entry: Entity): Param<T> = JsonParam(this.name, valueOf(entry))
}
