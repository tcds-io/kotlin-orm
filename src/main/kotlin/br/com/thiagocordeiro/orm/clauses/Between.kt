package br.com.thiagocordeiro.orm.clauses

import br.com.thiagocordeiro.orm.Column
import br.com.thiagocordeiro.orm.param.Param

class Between<T>(val column: Column<T>, private val first: T, private val last: T) : Clause {
    override fun toString(): String = "${column.name} BETWEEN ? AND ?"
    override fun params(): List<Param<*>> = TODO("Not yet implemented")
}
