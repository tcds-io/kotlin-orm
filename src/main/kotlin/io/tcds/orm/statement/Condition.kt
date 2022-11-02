package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Param

interface Condition {
    val column: Column<*, *>
    override fun toString(): String
    fun params(): List<Param<*, *>>
}
