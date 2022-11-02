package io.tcds.orm

import io.tcds.orm.driver.Connection
import io.tcds.orm.extension.equalsTo

class EntityRepository<Entity, PKType>(
    private val table: EntityTable<Entity, PKType>,
    connection: Connection,
) : Repository<Entity>(table, connection) {
    fun loadById(id: PKType): Entity? = loadBy(listOf(table.id equalsTo id))
}
