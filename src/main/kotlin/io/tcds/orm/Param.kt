package io.tcds.orm

import java.sql.PreparedStatement

interface Param<T> {
    val name: String
    val value: T

    fun bind(index: Int, stmt: PreparedStatement)
    fun describe(): String = "${name}=$value"
}
