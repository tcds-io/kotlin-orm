package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.Param

interface Clause {
    val column: Column<*, *>
    override fun toString(): String
    fun params(): List<Param<*, *>>
}
