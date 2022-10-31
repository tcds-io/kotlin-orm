package br.com.thiagocordeiro.orm.extension

import br.com.thiagocordeiro.orm.Column
import br.com.thiagocordeiro.orm.type.IntegerColumn
import br.com.thiagocordeiro.orm.clauses.Order
import br.com.thiagocordeiro.orm.type.StringColumn
import fixtures.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MapOrderExtensionTests {
    @Test
    fun `when order maps is empty then order statement is empty`() {
        val order: Map<Column<*>, Order> = emptyMap()

        Assertions.assertEquals("", order.toOrderByStatement())
    }

    @Test
    fun `when order maps is not empty then order statement has column order configuration`() {
        val id = IntegerColumn<User>("id")
        val column = StringColumn<User>("name")

        val order: Map<Column<*>, Order> = mapOf(
            id to Order.ASC,
            column to Order.DESC,
        )

        Assertions.assertEquals("ORDER BY id ASC, name DESC", order.toOrderByStatement())
    }
}
