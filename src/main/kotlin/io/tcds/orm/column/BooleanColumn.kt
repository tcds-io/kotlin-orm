package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Types

class BooleanColumn<Entity>(name: String, value: (Entity) -> Boolean?) : Column<Entity, Boolean?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: Boolean?) {
        when (value) {
            null -> stmt.setNull(index, Types.BOOLEAN)
            else -> stmt.setBoolean(index, value)
        }
    }
}
