package br.com.thiagocordeiro.orm.clauses

import br.com.thiagocordeiro.orm.param.Param

interface Clause {
    override fun toString(): String
    fun params(): List<Param<*>>
}
