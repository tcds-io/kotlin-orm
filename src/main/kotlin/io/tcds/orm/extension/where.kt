package io.tcds.orm.extension

import io.tcds.orm.Condition
import io.tcds.orm.Param
import io.tcds.orm.param.*
import io.tcds.orm.statement.Operator
import io.tcds.orm.statement.Operator.AND
import io.tcds.orm.statement.Operator.NONE
import io.tcds.orm.statement.Operator.OR
import io.tcds.orm.statement.Operator.WHERE
import io.tcds.orm.statement.Raw
import io.tcds.orm.statement.Statement
import io.tcds.orm.statement.StatementGroup
import java.time.Instant

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
fun group(condition: Condition): Statement = Statement(mutableListOf(Pair(NONE, condition)))
fun where(query: String, vararg params: Param<*>): Statement = Statement(mutableListOf(Pair(WHERE, Raw(query, *params))))
fun group(query: String, vararg params: Param<*>): Statement = Statement(mutableListOf(Pair(NONE, Raw(query, *params))))

infix fun Statement.add(condition: Condition) = add(Pair(NONE, condition)).let { this }
infix fun Statement.and(condition: Condition) = add(Pair(AND, condition)).let { this }
infix fun Statement.or(condition: Condition) = add(Pair(OR, condition)).let { this }
fun Statement.and(query: String, vararg params: Param<*>) = add(Pair(AND, Raw(query, *params))).let { this }
fun Statement.or(query: String, vararg params: Param<*>) = add(Pair(OR, Raw(query, *params))).let { this }

infix fun Statement.and(block: (stmt: Statement) -> Statement) = add(Pair(AND, StatementGroup(block(emptyWhere()).conditions))).let { this }
infix fun Statement.or(block: (stmt: Statement) -> Statement) = add(Pair(OR, StatementGroup(block(emptyWhere()).conditions))).let { this }

fun param(value: String, name: String = "") = StringParam(name, value)
fun param(value: Boolean, name: String = "") = BooleanParam(name, value)
fun param(value: Double, name: String = "") = DoubleParam(name, value)
fun param(value: Float, name: String = "") = FloatParam(name, value)
fun param(value: Instant, name: String = "") = InstantParam(name, value)
fun param(value: Int, name: String = "") = IntegerParam(name, value)
fun param(value: Long, name: String = "") = LongParam(name, value)
fun <T : Enum<*>> param(value: T, name: String = "") = EnumParam(name, value)
fun <T> param(value: T, name: String = "") = JsonParam(name, value)
