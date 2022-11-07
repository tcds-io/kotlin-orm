package io.tcds.orm

interface Condition {
    fun toSql(): String
    fun params(): List<Param<*, *>>
}
