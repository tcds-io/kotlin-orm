package io.tcds.orm.driver

import io.tcds.orm.OrmResultSet
import io.tcds.orm.ResultSetEntry

interface JdbcResultSetEntry<E> : ResultSetEntry<E> {
    override fun entry(row: OrmResultSet): E = entry(row as JdbcOrmResultSet)
    fun entry(row: JdbcOrmResultSet): E
}
