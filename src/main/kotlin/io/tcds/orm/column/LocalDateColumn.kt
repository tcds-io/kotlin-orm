package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.Date
import java.sql.PreparedStatement
import java.time.LocalDate

class LocalDateColumn<Entity>(
    name: String,
    value: (Entity) -> LocalDate,
) : Column<Entity, LocalDate>(name, value) {
    override fun columnType(): String = "DATE"
    override fun bind(stmt: PreparedStatement, index: Int, value: LocalDate) = stmt.setDate(index, Date.valueOf(value))
}
