package io.tcds.orm

import java.sql.PreparedStatement

interface Param<T> {
    val name: String
    val value: T

    fun bind(stmt: PreparedStatement, index: Int)
    fun describe(): String = "$name=$value"
}
