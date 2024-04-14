package io.tcds.orm.column

import io.tcds.orm.Column
import java.sql.PreparedStatement as Stmt
import java.sql.Timestamp
import java.util.Date

class DateColumn<Entity>(
    name: String,
    value: (Entity) -> Date,
) : Column<Entity, Date>(name, value) {
    override fun columnType(): String = "DATE"
    override fun bind(stmt: Stmt, index: Int, value: Date) = stmt.setTimestamp(index, Timestamp(value.time))
}
