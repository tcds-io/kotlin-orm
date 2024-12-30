package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.BooleanParam

class BooleanColumn<Entity>(
    name: String,
    value: (Entity) -> Boolean,
) : Column<Entity, Boolean>(name, value) {
    override fun columnType(): String = "BOOLEAN"
    override fun valueParam(value: Boolean): Param<Boolean> = BooleanParam(this.name, value)
    override fun entryParam(entry: Entity): Param<Boolean> = BooleanParam(this.name, valueOf(entry))
    override fun ddl(): String = "`$name` BOOLEAN NOT NULL"
}
