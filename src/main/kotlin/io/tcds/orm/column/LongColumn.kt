package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Types

class LongColumn<Entity>(name: String, value: (Entity) -> Long?) : Column<Entity, Long?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: Long?) {
        when (value) {
            null -> stmt.setNull(index, Types.BIGINT)
            else -> stmt.setLong(index, value.toLong())
        }
    }
}
