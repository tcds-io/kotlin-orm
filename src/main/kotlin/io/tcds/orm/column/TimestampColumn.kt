package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.Timestamp
import java.time.Instant
import java.sql.PreparedStatement as Stmt

class TimestampColumn<Entity>(
    name: String,
    value: (Entity) -> Instant,
) : Column<Entity, Instant>(name, value) {
    override fun columnType(): String = "TIMESTAMP"
    override fun bind(stmt: Stmt, index: Int, value: Instant) = stmt.setTimestamp(index, Timestamp.from(value))
}
