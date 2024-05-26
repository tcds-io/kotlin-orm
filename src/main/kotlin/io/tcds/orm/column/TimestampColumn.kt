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
    override fun toParam(value: Instant): Param<Instant> = InstantParam(this.name, value)
}
