package io.tcds.orm.extension

import io.tcds.orm.Condition
import io.tcds.orm.Param
import io.tcds.orm.statement.Operator
import io.tcds.orm.statement.Operator.AND
import io.tcds.orm.statement.Operator.NONE
import io.tcds.orm.statement.Operator.OR
import io.tcds.orm.statement.Operator.WHERE
import io.tcds.orm.statement.Raw
import io.tcds.orm.statement.Statement
import io.tcds.orm.statement.StatementGroup

fun List<Param<*>>.columns(): String = joinToString(", ") { it.name }
fun List<Param<*>>.marks(): String = joinToString(", ") { "?" }
fun List<Param<*>>.columnsEqualMarks(): String = joinToString(", ") { "${it.name} = ?" }
fun Pair<Operator, Condition>.toStmt() = if (first == NONE) second.toStmt() else "${first.operator} ${second.toStmt()}"
fun Pair<Operator, Condition>.toSql() = if (first == NONE) second.toSql() else "${first.operator} ${second.toSql()}"

fun Pair<Operator, Condition>.hasConditions() = if (second is StatementGroup) !(second as StatementGroup).isEmpty() else true
fun MutableList<Pair<Operator, Condition>>.toStmt() = filter { it.hasConditions() }.joinToString(" ") { it.toStmt() }
fun MutableList<Pair<Operator, Condition>>.toSql() = filter { it.hasConditions() }.joinToString(" ") { it.toSql() }
fun MutableList<Pair<Operator, Condition>>.removeWhere(): MutableList<Pair<Operator, Condition>> {
    return map { if (it.first == WHERE) Pair(NONE, it.second) else it }.toMutableList()
}

fun emptyWhere(): Statement = Statement(mutableListOf())
fun emptyParams(): List<Param<*>> = emptyWhere().params()
fun where(condition: Condition): Statement = Statement(mutableListOf(Pair(WHERE, condition)))
fun where(query: String, params: List<Param<*>> = emptyList()): Statement = Statement(mutableListOf(Pair(WHERE, Raw(query, params))))
fun group(condition: Condition): Statement = Statement(mutableListOf(Pair(NONE, condition)))
fun group(query: String, params: List<Param<*>> = emptyList()): Statement = Statement(mutableListOf(Pair(NONE, Raw(query, params))))

infix fun Statement.add(condition: Condition) = add(Pair(NONE, condition)).let { this }
infix fun Statement.and(condition: Condition) = add(Pair(AND, condition)).let { this }
infix fun Statement.or(condition: Condition) = add(Pair(OR, condition)).let { this }
fun Statement.and(query: String, params: List<Param<*>> = emptyList()) = add(Pair(AND, Raw(query, params))).let { this }
fun Statement.or(query: String, params: List<Param<*>> = emptyList()) = add(Pair(OR, Raw(query, params))).let { this }

infix fun Statement.and(block: (stmt: Statement) -> Statement) = add(Pair(AND, StatementGroup(block(emptyWhere()).conditions))).let { this }
infix fun Statement.or(block: (stmt: Statement) -> Statement) = add(Pair(OR, StatementGroup(block(emptyWhere()).conditions))).let { this }
