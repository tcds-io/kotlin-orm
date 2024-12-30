package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.LongParam

class LongColumn<Entity>(
    name: String,
    value: (Entity) -> Long,
) : Column<Entity, Long>(name, value) {
    override fun columnType(): String = "LONG"
    override fun valueParam(value: Long): Param<Long> = LongParam(this.name, value)
    override fun entryParam(entry: Entity): Param<Long> = LongParam(this.name, valueOf(entry))
    override fun ddl(): String = "`$name` BIGINT NOT NULL"
}
