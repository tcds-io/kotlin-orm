package io.tcds.orm.extension

import io.tcds.orm.Condition
import io.tcds.orm.Param
import io.tcds.orm.statement.StatementGroup
import io.tcds.orm.statement.Operator
import io.tcds.orm.statement.Statement

fun List<Param<*, *>>.columns(): String = joinToString(", ") { it.column.name }
fun List<Param<*, *>>.marks(): String = joinToString(", ") { "?" }
fun Pair<Operator, Condition>.toSql() = if (first == Operator.NONE) second.toSql() else "${first.operator} ${second.toSql()}"
fun MutableList<Pair<Operator, Condition>>.toSql() = joinToString(" ") { it.toSql() }

fun emptyWhere(): Statement = Statement(mutableListOf())
fun where(condition: Condition): Statement = Statement(mutableListOf(Pair(Operator.WHERE, condition)))
fun stmt(condition: Condition): Statement = Statement(mutableListOf(Pair(Operator.NONE, condition)))

infix fun Statement.and(condition: Condition) = conditions.add(Pair(Operator.AND, condition)).let { this }
infix fun Statement.or(condition: Condition) = conditions.add(Pair(Operator.OR, condition)).let { this }

infix fun Statement.and(block: () -> Statement) = add(Pair(Operator.AND, StatementGroup(block().conditions))).let { this }
infix fun Statement.or(block: () -> Statement) = add(Pair(Operator.OR, StatementGroup(block().conditions))).let { this }
