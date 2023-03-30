package io.tcds.orm

import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where

interface EntityRepository<Entity, PKType> : Repository<Entity> {
    override val table: EntityTable<Entity, PKType>

    fun loadById(id: PKType): Entity? = loadBy(where(table.id equalsTo id))
    fun update(vararg entries: Entity) = entries.forEach { update(it, table.columns) }
    fun delete(vararg entities: Entity) = entities.forEach { delete(where(table.id equalsTo table.id.valueOf(it))) }

    fun update(entry: Entity, columns: List<Column<Entity, *>>) {
        update(entry, columns, where(table.id equalsTo table.id.valueOf(entry)))
    }
}
