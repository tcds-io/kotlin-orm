package io.tcds.orm.driver

import io.tcds.orm.OrmResultSet
import io.tcds.orm.ResultSetEntry

interface JdbcResultSetEntry<E> : ResultSetEntry<E> {
    override suspend fun entry(row: OrmResultSet): E = entry(row as JdbcOrmResultSet)
    suspend fun entry(row: JdbcOrmResultSet): E
}
