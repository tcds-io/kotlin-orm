package br.com.thiagocordeiro.orm.param

import br.com.thiagocordeiro.orm.Column

class Param<T>(val column: Column<T>, value: T)
