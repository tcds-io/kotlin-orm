package io.tcds.orm

import io.tcds.orm.driver.Connection

abstract class EntityTable<Entity, PKType>(
    connection: Connection,
    override val tableName: String,
    val id: Column<Entity, PKType?>,
    override val softDelete: Boolean = false,
) : Table<Entity>(connection, tableName, softDelete), EntityRepository<Entity, PKType> {
    override val table: EntityTable<Entity, PKType> get() = this

    init {
        column(id)
    }
}
