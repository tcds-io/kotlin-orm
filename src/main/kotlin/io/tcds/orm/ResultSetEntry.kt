package io.tcds.orm

interface ResultSetEntry<E> {
    fun entry(row: OrmResultSet): E
}
