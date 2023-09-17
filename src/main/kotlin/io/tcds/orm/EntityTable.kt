package io.tcds.orm

import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where

abstract class EntityTable<E, PKType>(
    connection: Connection,
    table: String,
    softDelete: Boolean = false,
) : Table<E>(connection, table, softDelete) {
    abstract val id: Column<E, PKType>

    fun loadById(id: PKType): E? = loadBy(where(this.id equalsTo id))

    fun update(vararg entities: E) = entities.map {
        update(
            columns.filter { col -> col != id }.map { column -> column.toValueParam(entry = it) },
            where(this.id equalsTo this.id.valueOf(it)),
        )
    }

    fun delete(vararg entities: E) = entities.map { delete(where(this.id equalsTo this.id.valueOf(it))) }
}
