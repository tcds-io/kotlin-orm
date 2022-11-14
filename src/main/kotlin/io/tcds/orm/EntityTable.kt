package io.tcds.orm

abstract class EntityTable<E, pkT>(
    override val tableName: String,
    val id: Column<E, pkT?>,
    override val softDelete: Boolean = false,
) : Table<E>(tableName, softDelete) {
    init {
        column(id)
    }
}
