package io.tcds.orm

import java.time.LocalDate
import java.time.LocalDateTime

interface OrmResultSet {
    fun value(columnName: String): String?

    fun get(column: Column<*, String>): String
    fun get(column: Column<*, Int>): Int
    fun get(column: Column<*, Long>): Long
    fun get(column: Column<*, Float>): Float
    fun get(column: Column<*, Double>): Double
    fun get(column: Column<*, Boolean>): Boolean
    fun get(column: Column<*, LocalDate>): LocalDate
    fun get(column: Column<*, LocalDateTime>): LocalDateTime

    fun nullable(column: Column<*, String?>): String?
    fun nullable(column: Column<*, Int?>): Int?
    fun nullable(column: Column<*, Long?>): Long?
    fun nullable(column: Column<*, Float?>): Float?
    fun nullable(column: Column<*, Double?>): Double?
    fun nullable(column: Column<*, LocalDate?>): LocalDate?
    fun nullable(column: Column<*, LocalDateTime?>): LocalDateTime?
}
