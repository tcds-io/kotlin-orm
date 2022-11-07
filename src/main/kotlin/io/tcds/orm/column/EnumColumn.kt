package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Types

class EnumColumn<Entity, T : Enum<*>>(name: String, value: (Entity) -> T?) : Column<Entity, T?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: T?) {
        when (value) {
            null -> stmt.setNull(index, Types.BOOLEAN)
            else -> stmt.setString(index, value.name)
        }
    }
}
