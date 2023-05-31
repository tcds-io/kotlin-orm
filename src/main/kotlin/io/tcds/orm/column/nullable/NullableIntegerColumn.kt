package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Types

class NullableIntegerColumn<Entity>(name: String, value: (Entity) -> Int?) : Column<Entity, Int?>(name, value) {
    override fun columnType(): String = "INTEGER NULL"

    override fun bind(stmt: PreparedStatement, index: Int, value: Int?) {
        when (value) {
            null -> stmt.setNull(index, Types.INTEGER)
            else -> stmt.setInt(index, value)
        }
    }
}
