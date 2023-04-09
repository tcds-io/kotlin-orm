package io.tcds.orm

interface ResultSetEntry<E> {
    suspend fun entry(row: OrmResultSet): E
}
