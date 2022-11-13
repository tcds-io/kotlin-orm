package io.tcds.orm

import java.sql.ResultSet as JdbcResultSet

class OrmResultSet(val rs: JdbcResultSet) {
    fun <T> nullableValue(value: T): T? = if (rs.wasNull()) null else value
}
