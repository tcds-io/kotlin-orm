package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Types

class FloatColumn<Entity>(name: String, value: (Entity) -> Float?) : Column<Entity, Float?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: Float?) {
        when (value) {
            null -> stmt.setNull(index, Types.FLOAT)
            else -> stmt.setFloat(index, value)
        }
    }
}
