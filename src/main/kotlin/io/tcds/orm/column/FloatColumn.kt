package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement

class FloatColumn<Entity>(name: String, value: (Entity) -> Float) : Column<Entity, Float>(name, value) {
    override fun columnType(): String = "FLOAT"
    override fun bind(stmt: PreparedStatement, index: Int, value: Float) = stmt.setFloat(index, value)
}
