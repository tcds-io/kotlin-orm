package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.IntegerParam

class IntegerColumn<Entity>(
    name: String,
    value: (Entity) -> Int,
) : Column<Entity, Int>(name, value) {
    override fun columnType(): String = "INTEGER"
    override fun valueParam(value: Int): Param<Int> = IntegerParam(this.name, value)
    override fun entryParam(entry: Entity): Param<Int> = IntegerParam(this.name, valueOf(entry))
    override fun ddl(): String = "`$name` INT NOT NULL"
}
