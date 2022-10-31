package br.com.thiagocordeiro.orm

abstract class EntityTable<E>(override val name: String, val primaryKey: Column<E>) : Table<E>(name) {
    init {
        registerColumn(primaryKey)
    }
}
