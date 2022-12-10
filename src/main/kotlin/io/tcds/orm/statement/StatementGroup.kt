package io.tcds.orm.statement

import io.tcds.orm.Condition
import io.tcds.orm.Param
import io.tcds.orm.extension.toSql
import io.tcds.orm.extension.toStmt

data class StatementGroup(private val conditions: MutableList<Pair<Operator, Condition>>) : Condition {
    override fun params(): List<Param<*, *>> {
        val params = mutableListOf<Param<*, *>>()
        conditions.forEach { params.addAll(it.second.params()) }

        return params
    }

    override fun toStmt(): String = "(${conditions.toStmt()})"
    override fun toSql(): String = "(${conditions.toSql()})"
}
