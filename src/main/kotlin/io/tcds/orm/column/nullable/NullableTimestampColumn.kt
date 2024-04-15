package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.sql.Types
import java.time.Instant

class NullableTimestampColumn<Entity>(
    name: String,
    value: (Entity) -> Instant?,
) : Column<Entity, Instant?>(name, value) {
    override fun columnType(): String = "DATETIME NULL"

    override fun bind(stmt: PreparedStatement, index: Int, value: Instant?) {
        when (value) {
            null -> stmt.setNull(index, Types.TIMESTAMP)
            else -> stmt.setTimestamp(index, Timestamp.from(value))
        }
    }
}
