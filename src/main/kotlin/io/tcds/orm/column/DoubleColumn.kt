package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement

class DoubleColumn<Entity>(name: String, value: (Entity) -> Double) : Column<Entity, Double>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: Double) = stmt.setDouble(index, value)
}
