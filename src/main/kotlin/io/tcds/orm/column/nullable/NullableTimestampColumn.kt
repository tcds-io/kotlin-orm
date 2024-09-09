package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.nullable.NullableInstantParam
import java.time.Instant

class NullableTimestampColumn<Entity>(
    name: String,
    value: (Entity) -> Instant?,
) : Column<Entity, Instant?>(name, value) {
    override fun columnType(): String = "DATETIME NULL"
    override fun valueParam(value: Instant?): Param<Instant?> = NullableInstantParam(this.name, value)
    override fun entryParam(entry: Entity): Param<Instant?> = NullableInstantParam(this.name, valueOf(entry))
}
