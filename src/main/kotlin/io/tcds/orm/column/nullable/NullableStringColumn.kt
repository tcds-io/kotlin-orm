package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Types

class NullableStringColumn<Entity>(name: String, value: (Entity) -> String?) : Column<Entity, String?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: String?) {
        when (value) {
            null -> stmt.setNull(index, Types.VARCHAR)
            else -> stmt.setString(index, value)
        }
    }
}
