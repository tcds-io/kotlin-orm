package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.nullable.NullableIntegerParam

class NullableIntegerColumn<Entity>(name: String, value: (Entity) -> Int?) : Column<Entity, Int?>(name, value) {
    override fun columnType(): String = "INTEGER NULL"
    override fun toParam(value: Int?): Param<Int?> = NullableIntegerParam(this.name, value)
}
