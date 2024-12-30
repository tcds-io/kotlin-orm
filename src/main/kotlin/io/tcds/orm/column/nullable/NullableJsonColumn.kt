package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.nullable.NullableJsonParam

class NullableJsonColumn<Entity, T>(name: String, value: (Entity) -> T?) : Column<Entity, T?>(name, value) {
    override fun columnType(): String = "JSON NULL"
    override fun valueParam(value: T?): Param<T?> = NullableJsonParam(this.name, value)
    override fun entryParam(entry: Entity): Param<T?> = NullableJsonParam(this.name, valueOf(entry))
    override fun ddl(): String = "`$name` JSON"
}
