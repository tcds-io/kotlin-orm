package io.tcds.orm

import io.tcds.orm.driver.Connection
import io.tcds.orm.extension.equalsTo

class EntityRepository<Entity, PKType>(
    private val table: EntityTable<Entity, PKType>,
    connection: Connection,
) : Repository<Entity>(table, connection) {
    fun delete(vararg entities: Entity) = entities.forEach { delete(listOf(table.id equalsTo table.id.valueOf(it))) }

    fun loadById(id: PKType): Entity? = loadBy(listOf(table.id equalsTo id))
}
