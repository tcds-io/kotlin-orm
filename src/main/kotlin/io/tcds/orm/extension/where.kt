package io.tcds.orm.extension

import io.tcds.orm.Condition
import io.tcds.orm.Param
import io.tcds.orm.statement.Operator
import io.tcds.orm.statement.Operator.AND
import io.tcds.orm.statement.Operator.NONE
import io.tcds.orm.statement.Operator.OR
import io.tcds.orm.statement.Operator.WHERE
import io.tcds.orm.statement.Statement
import io.tcds.orm.statement.StatementGroup

fun List<Param<*, *>>.columns(): String = joinToString(", ") { it.column.name }
fun List<Param<*, *>>.marks(): String = joinToString(", ") { "?" }
fun List<Param<*, *>>.columnsEqualMarks(): String = joinToString(", ") { "${it.column.name} = ?" }
fun Pair<Operator, Condition>.toStmt() = if (first == NONE) second.toStmt() else "${first.operator} ${second.toStmt()}"
fun Pair<Operator, Condition>.toSql() = if (first == NONE) second.toSql() else "${first.operator} ${second.toSql()}"

fun MutableList<Pair<Operator, Condition>>.toStmt() = joinToString(" ") { it.toStmt() }
fun MutableList<Pair<Operator, Condition>>.toSql() = joinToString(" ") { it.toSql() }
fun MutableList<Pair<Operator, Condition>>.removeWhere(): MutableList<Pair<Operator, Condition>> {
    return map { if (it.first == WHERE) Pair(NONE, it.second) else it }.toMutableList()
}

fun emptyWhere(): Statement = Statement(mutableListOf())
fun emptyParams(): List<Param<*, *>> = emptyWhere().params()
fun where(condition: Condition): Statement = Statement(mutableListOf(Pair(WHERE, condition)))
fun stmt(condition: Condition): Statement = Statement(mutableListOf(Pair(NONE, condition)))

infix fun Statement.and(condition: Condition) = conditions.add(Pair(AND, condition)).let { this }
infix fun Statement.or(condition: Condition) = conditions.add(Pair(OR, condition)).let { this }

infix fun Statement.and(block: () -> Statement) = add(Pair(AND, StatementGroup(block().conditions))).let { this }
infix fun Statement.or(block: () -> Statement) = add(Pair(OR, StatementGroup(block().conditions))).let { this }
