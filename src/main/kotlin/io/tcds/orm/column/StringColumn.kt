package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.Param
import io.tcds.orm.param.StringParam

class StringColumn<Entity>(
    name: String,
    value: (Entity) -> String,
) : Column<Entity, String>(name, value) {
    override fun columnType(): String = "STRING"
    override fun toParam(value: String): Param<String> = StringParam(this.name, value)
}
