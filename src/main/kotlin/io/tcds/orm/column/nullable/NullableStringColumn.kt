package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.nullable.NullableStringParam

class NullableStringColumn<Entity>(name: String, value: (Entity) -> String?) : Column<Entity, String?>(name, value) {
    override fun columnType(): String = "STRING NULL"
    override fun toParam(value: String?): Param<String?> = NullableStringParam(this.name, value)
}
