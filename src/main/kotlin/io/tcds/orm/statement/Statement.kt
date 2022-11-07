package io.tcds.orm.statement

import io.tcds.orm.Condition
import io.tcds.orm.Param
import io.tcds.orm.extension.toSql

open class Statement(val conditions: MutableList<Pair<Operator, Condition>>) {
    fun toSql() = conditions.toSql()

    fun add(condition: Pair<Operator, Condition>) = conditions.add(condition)

    fun params(): List<Param<*, *>> {
        val params = mutableListOf<Param<*, *>>()
        conditions.forEach { params.addAll(it.second.params()) }

        return params
    }
}

