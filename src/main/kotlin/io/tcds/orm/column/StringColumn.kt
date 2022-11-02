package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Types

class StringColumn<Entity>(name: String, value: (Entity) -> String?) : Column<Entity, String?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: String?) {
        when (value) {
            null -> stmt.setNull(index, Types.BOOLEAN)
            else -> stmt.setString(index, value)
        }
    }
}
