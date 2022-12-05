package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Types

class DoubleColumn<Entity>(name: String, value: (Entity) -> Double?) : Column<Entity, Double?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: Double?) {
        when (value) {
            null -> stmt.setNull(index, Types.DOUBLE)
            else -> stmt.setDouble(index, value)
        }
    }
}
