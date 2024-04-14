package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.sql.Types
import java.util.Date

class NullableDateColumn<Entity>(
    name: String,
    value: (Entity) -> Date?,
) : Column<Entity, Date?>(name, value) {
    override fun columnType(): String = "DATETIME NULL"

    override fun bind(stmt: PreparedStatement, index: Int, value: Date?) {
        when (value) {
            null -> stmt.setNull(index, Types.TIMESTAMP)
            else -> stmt.setTimestamp(index, Timestamp(value.time))
        }
    }
}
