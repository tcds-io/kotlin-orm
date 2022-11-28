package io.tcds.orm.column

import io.tcds.orm.Column
import java.math.BigDecimal
import java.sql.PreparedStatement
import java.sql.Types

class DecimalColumn<Entity>(name: String, value: (Entity) -> BigDecimal?) : Column<Entity, BigDecimal?>(name, value) {
    override fun bind(stmt: PreparedStatement, index: Int, value: BigDecimal?) {
        when (value) {
            null -> stmt.setNull(index, Types.DOUBLE)
            else -> stmt.setBigDecimal(index, value)
        }
    }
}
