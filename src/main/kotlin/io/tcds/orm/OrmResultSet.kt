package io.tcds.orm

import java.time.Instant

interface OrmResultSet {
    fun <T> value(columnName: String, clazz: Class<T>): T?

    fun get(column: Column<*, String>): String
    fun get(column: Column<*, Int>): Int
    fun get(column: Column<*, Long>): Long
    fun get(column: Column<*, Float>): Float
    fun get(column: Column<*, Double>): Double
    fun get(column: Column<*, Boolean>): Boolean
    fun get(column: Column<*, Instant>): Instant

    fun nullable(column: Column<*, String?>): String?
    fun nullable(column: Column<*, Int?>): Int?
    fun nullable(column: Column<*, Long?>): Long?
    fun nullable(column: Column<*, Float?>): Float?
    fun nullable(column: Column<*, Double?>): Double?
    fun nullable(column: Column<*, Instant?>): Instant?
}
