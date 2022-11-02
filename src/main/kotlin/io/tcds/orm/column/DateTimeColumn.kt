package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.sql.Types
import java.time.LocalDateTime

class DateTimeColumn<Entity>(
    name: String,
    value: (Entity) -> LocalDateTime?,
) : Column<Entity, LocalDateTime?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: LocalDateTime?) {
        when (value) {
            null -> stmt.setNull(index, Types.TIMESTAMP)
            else -> stmt.setTimestamp(index, Timestamp.valueOf(value))
        }
    }
}
