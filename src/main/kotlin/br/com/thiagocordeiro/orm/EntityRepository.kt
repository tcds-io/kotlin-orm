package br.com.thiagocordeiro.orm

import br.com.thiagocordeiro.orm.clauses.equalsTo

class EntityRepository<E>(
    private val table: EntityTable<E>,
    private val connection: Connection,
) : Repository<E>(table, connection) {
    fun loadById(id: String): E? = selectOne(listOf(table.primaryKey equalsTo id))
    fun loadById(id: Int): E? = selectOne(listOf(table.primaryKey equalsTo id))
}
