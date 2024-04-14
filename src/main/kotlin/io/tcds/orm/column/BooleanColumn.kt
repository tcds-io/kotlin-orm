package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement

class BooleanColumn<Entity>(
    name: String,
    value: (Entity) -> Boolean,
) : Column<Entity, Boolean>(name, value) {
    override fun columnType(): String = "BOOLEAN"
    override fun bind(stmt: PreparedStatement, index: Int, value: Boolean) = stmt.setBoolean(index, value)
}
