package io.tcds.orm

interface Condition {
    fun toStmt(): String
    fun toSql(): String
    fun params(): List<Param<*>>
}
