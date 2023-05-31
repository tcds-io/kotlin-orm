package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Types

class NullableDoubleColumn<Entity>(name: String, value: (Entity) -> Double?) : Column<Entity, Double?>(name, value) {
    override fun columnType(): String = "DOUBLE NULL"

    override fun bind(stmt: PreparedStatement, index: Int, value: Double?) {
        when (value) {
            null -> stmt.setNull(index, Types.DOUBLE)
            else -> stmt.setDouble(index, value)
        }
    }
}
