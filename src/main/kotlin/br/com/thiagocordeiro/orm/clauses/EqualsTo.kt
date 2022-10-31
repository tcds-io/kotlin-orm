package br.com.thiagocordeiro.orm.clauses

import br.com.thiagocordeiro.orm.Column
import br.com.thiagocordeiro.orm.param.Param

class EqualsTo(val column: Column<*>, val value: Any) : Clause {
    override fun toString(): String = "${column.name} = ?"
    override fun params(): List<Param<*>> = TODO("Not yet implemented")
}
