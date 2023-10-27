package io.tcds.orm.column.nullable

import io.tcds.orm.Column
import io.tcds.orm.param.JsonParam
import java.sql.PreparedStatement
import java.sql.Types

class NullableJsonColumn<Entity, T>(name: String, value: (Entity) -> T?) : Column<Entity, T?>(name, value) {
    override fun columnType(): String = "JSON NULL"

    override fun bind(stmt: PreparedStatement, index: Int, value: T?) {
        when (value) {
            null -> stmt.setNull(index, Types.VARCHAR)
            else -> stmt.setString(index, JsonParam.mapper.writeValueAsString(value))
        }
    }
}
