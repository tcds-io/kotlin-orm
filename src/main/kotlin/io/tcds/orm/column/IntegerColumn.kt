package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement

class IntegerColumn<Entity>(name: String, value: (Entity) -> Int) : Column<Entity, Int>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: Int) = stmt.setInt(index, value)
}
