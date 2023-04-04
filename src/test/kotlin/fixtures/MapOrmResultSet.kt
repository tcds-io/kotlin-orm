package fixtures

import io.tcds.orm.Column
import io.tcds.orm.OrmResultSet
import java.time.LocalDateTime

typealias OrmResult<T> = Map<Column<*, T>, T>

class MapOrmResultSet(private val data: OrmResult<*>) : OrmResultSet {
    override fun get(column: Column<*, String>): String = data[column]!! as String
    override fun get(column: Column<*, Int>): Int = data[column]!! as Int
    override fun get(column: Column<*, Long>): Long = data[column]!! as Long
    override fun get(column: Column<*, Float>): Float = data[column]!! as Float
    override fun get(column: Column<*, Double>): Double = data[column]!! as Double
    override fun get(column: Column<*, Boolean>): Boolean = data[column]!! as Boolean
    override fun get(column: Column<*, LocalDateTime>): LocalDateTime = data[column]!! as LocalDateTime

    override fun nullable(column: Column<*, String?>): String? = data[column]!! as String?
    override fun nullable(column: Column<*, Int?>): Int? = data[column]!! as Int?
    override fun nullable(column: Column<*, Long?>): Long? = data[column]!! as Long?
    override fun nullable(column: Column<*, Float?>): Float? = data[column]!! as Float?
    override fun nullable(column: Column<*, Double?>): Double? = data[column]!! as Double?
    override fun nullable(column: Column<*, LocalDateTime?>): LocalDateTime? = data[column]!! as LocalDateTime?
}
