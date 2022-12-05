package io.tcds.orm

import io.tcds.orm.driver.Connection
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where

class EntityRepository<Entity, PKType>(
    private val table: EntityTable<Entity, PKType>,
    connection: Connection,
) : Repository<Entity>(table, connection) {
    fun delete(vararg entities: Entity) = entities.forEach { delete(where(table.id equalsTo table.id.valueOf(it))) }

    fun loadById(id: PKType): Entity? = loadBy(where(table.id equalsTo id))
}
