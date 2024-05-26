package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.LongParam

class LongColumn<Entity>(
    name: String,
    value: (Entity) -> Long,
) : Column<Entity, Long>(name, value) {
    override fun columnType(): String = "LONG"
    override fun toParam(value: Long): Param<Long> = LongParam(this.name, value)
}
