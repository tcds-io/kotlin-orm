package io.tcds.orm

abstract class EntityTable<E, pkT>(override val table: String, val id: Column<E, pkT?>) : Table<E>(table) {
    init {
        registerColumn(id)
    }
}
