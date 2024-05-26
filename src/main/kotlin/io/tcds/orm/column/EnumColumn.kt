package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.EnumParam

class EnumColumn<Entity, T : Enum<*>>(
    name: String,
    value: (Entity) -> T,
) : Column<Entity, T>(name, value) {
    override fun columnType(): String = "ENUM"
    override fun toParam(value: T): Param<T> = EnumParam(this.name, value)
}
