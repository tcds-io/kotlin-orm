package io.tcds.orm

import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where

interface EntityRepository<Entity, PKType> : Repository<Entity> {
    override val table: EntityTable<Entity, PKType>

    fun delete(vararg entities: Entity) = entities.forEach { delete(where(table.id equalsTo table.id.valueOf(it))) }

    fun loadById(id: PKType): Entity? = loadBy(where(table.id equalsTo id))
}
