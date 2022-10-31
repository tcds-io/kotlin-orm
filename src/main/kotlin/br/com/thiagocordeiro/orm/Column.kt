package br.com.thiagocordeiro.orm

import java.sql.Statement

abstract class Column<T>(val name: String) {
    abstract fun bind(index: Int, stmt: Statement, value: T)
}
