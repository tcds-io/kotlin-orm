package io.tcds.orm.column

import io.tcds.orm.Column
import io.tcds.orm.param.JsonParam
import java.sql.PreparedStatement as Stmt

class JsonColumn<Entity, T>(
    name: String,
    value: (Entity) -> T,
) : Column<Entity, T>(name, value) {
    override fun columnType(): String = "JSON"
    override fun bind(stmt: Stmt, index: Int, value: T) = stmt.setString(index, JsonParam.mapper.writeValueAsString(value))
}
