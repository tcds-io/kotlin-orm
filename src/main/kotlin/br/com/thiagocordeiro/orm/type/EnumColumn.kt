package br.com.thiagocordeiro.orm.type

import br.com.thiagocordeiro.orm.Column
import java.sql.Statement

class EnumColumn<Entity, Type>(name: String) : Column<Entity>(name) {
    override fun bind(index: Int, stmt: Statement, value: Entity): Unit = TODO("Not yet implemented")
}
