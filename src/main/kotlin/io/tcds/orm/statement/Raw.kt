package io.tcds.orm.statement

import io.tcds.orm.Condition
import io.tcds.orm.Param

class Raw(
    private val query: String,
    vararg val params: Param<*>,
) : Condition {
    override fun toStmt(): String = query
    override fun toSql(): String = params.fold(query) { folded, param ->
        folded.replaceFirst("?", "`${param.value}`")
    }

    override fun params(): List<Param<*>> = params.toList()
}
