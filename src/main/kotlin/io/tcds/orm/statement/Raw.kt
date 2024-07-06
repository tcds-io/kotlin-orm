package io.tcds.orm.statement

import io.tcds.orm.Condition
import io.tcds.orm.Param

data class Raw(
    private val query: String,
    private val params: List<Param<*>>,
) : Condition {
    override fun toStmt(): String = query
    override fun toSql(): String = params.fold(query) { folded, param ->
        folded.replaceFirst("?", "`${param.value}`")
    }

    override fun params(): List<Param<*>> = params
}
