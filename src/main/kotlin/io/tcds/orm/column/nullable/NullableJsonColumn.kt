package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import io.tcds.orm.column.JsonColumn
import java.sql.PreparedStatement
import java.sql.Types

class NullableJsonColumn<Entity, T>(name: String, value: (Entity) -> T?) : Column<Entity, T?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: T?) {
        when (value) {
            null -> stmt.setNull(index, Types.VARCHAR)
            else -> stmt.setString(index, JsonColumn.mapper.writeValueAsString(value))
        }
    }
}