package io.tcds.orm.extension

import io.tcds.orm.Column
import io.tcds.orm.OrmResultSet

inline fun <reified T : Enum<T>> OrmResultSet.get(column: Column<*, T?>): T? {
    val value = rs.getString(column.name)

    return if (rs.wasNull()) null else enumValueOf<T>(value)
}
