package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.nullable.NullableEnumParam

class NullableEnumColumn<Entity, T : Enum<*>>(name: String, value: (Entity) -> T?) : Column<Entity, T?>(name, value) {
    override fun columnType(): String = "ENUM NULL"
    override fun valueParam(value: T?): Param<T?> = NullableEnumParam(this.name, value)
    override fun entryParam(entry: Entity): Param<T?> = NullableEnumParam(this.name, valueOf(entry))
    override fun ddl(): String = "`$name` VARCHAR(255)"
}
