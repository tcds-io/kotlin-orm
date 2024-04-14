package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement

class StringColumn<Entity>(
    name: String,
    value: (Entity) -> String,
) : Column<Entity, String>(name, value) {
    override fun columnType(): String = "STRING"
    override fun bind(stmt: PreparedStatement, index: Int, value: String) = stmt.setString(index, value)
}
