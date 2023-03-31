package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.Types
import java.time.LocalDate

class NullableDateColumn<Entity>(
    name: String,
    value: (Entity) -> LocalDate?,
) : Column<Entity, LocalDate?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: LocalDate?) {
        when (value) {
            null -> stmt.setNull(index, Types.DATE)
            else -> stmt.setDate(index, Date.valueOf(value))
        }
    }
}
