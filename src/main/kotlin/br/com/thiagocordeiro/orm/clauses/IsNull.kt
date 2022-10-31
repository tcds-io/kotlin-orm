package br.com.thiagocordeiro.orm.clauses

import br.com.thiagocordeiro.orm.Column
import br.com.thiagocordeiro.orm.param.Param

class IsNull(val column: Column<*>) : Clause {
    override fun toString(): String = "${column.name} IS NULL"
    override fun params(): List<Param<*>> = TODO("Not yet implemented")
}
