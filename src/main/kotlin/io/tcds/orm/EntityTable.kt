package io.tcds.orm

import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where

abstract class EntityTable<E, PKType>(
    connection: Connection,
    table: String,
    val id: Column<E, PKType>,
    softDelete: Boolean = false,
) : Table<E>(connection, table, softDelete) {
    init {
        column(id)
    }

    suspend fun loadById(id: PKType): E? = loadBy(where(this.id equalsTo id))

    suspend fun update(vararg entities: E) = entities.forEach {
        update(
            columns.filter { col -> col != id }.map { column -> column.toValueParam(entry = it) },
            where(this.id equalsTo this.id.valueOf(it)),
        )
    }

    suspend fun delete(vararg entities: E) = entities.forEach { delete(where(this.id equalsTo this.id.valueOf(it))) }
}
