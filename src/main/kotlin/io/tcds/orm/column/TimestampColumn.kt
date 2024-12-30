package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.InstantParam
import java.time.Instant

class TimestampColumn<Entity>(
    name: String,
    value: (Entity) -> Instant,
) : Column<Entity, Instant>(name, value) {
    override fun columnType(): String = "TIMESTAMP"
    override fun valueParam(value: Instant): Param<Instant> = InstantParam(this.name, value)
    override fun entryParam(entry: Entity): Param<Instant> = InstantParam(this.name, valueOf(entry))
    override fun ddl(): String = "`$name` TIMESTAMP NOT NULL"
}
