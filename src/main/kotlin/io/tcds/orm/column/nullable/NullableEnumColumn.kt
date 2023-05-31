package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Types

class NullableEnumColumn<Entity, T : Enum<*>>(name: String, value: (Entity) -> T?) : Column<Entity, T?>(name, value) {
    override fun columnType(): String = "ENUM NULL"

    override fun bind(stmt: PreparedStatement, index: Int, value: T?) {
        when (value) {
            null -> stmt.setNull(index, Types.VARCHAR)
            else -> stmt.setString(index, value.name)
        }
    }
}
