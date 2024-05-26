package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.IntegerParam

class IntegerColumn<Entity>(
    name: String,
    value: (Entity) -> Int,
) : Column<Entity, Int>(name, value) {
    override fun columnType(): String = "INTEGER"
    override fun toParam(value: Int): Param<Int> = IntegerParam(this.name, value)
}
