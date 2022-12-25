package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement

class LongColumn<Entity>(name: String, value: (Entity) -> Long) : Column<Entity, Long>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: Long) = stmt.setLong(index, value)
}
