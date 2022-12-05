package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Types

class IntegerColumn<Entity>(name: String, value: (Entity) -> Int?) : Column<Entity, Int?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: Int?) {
        when (value) {
            null -> stmt.setNull(index, Types.INTEGER)
            else -> stmt.setInt(index, value)
        }
    }
}
