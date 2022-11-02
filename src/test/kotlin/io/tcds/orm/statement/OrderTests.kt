package io.tcds.orm.statement

import io.tcds.orm.Column
import io.tcds.orm.column.IntegerColumn
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.toOrderByStatement
import fixtures.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OrderTests {
    @Test
    fun `when order maps is empty then order statement is empty`() {
        val order: Map<Column<User, *>, Order> = emptyMap()

        Assertions.assertEquals("", order.toOrderByStatement())
    }

    @Test
    fun `when order maps is not empty then order statement has column order configuration`() {
        val age = IntegerColumn<User>("age") { it.age }
        val name = StringColumn<User>("name") { it.name }

        val order: Map<Column<User, *>, Order> = mapOf(
            age to Order.ASC,
            name to Order.DESC,
        )

        Assertions.assertEquals("ORDER BY age ASC, name DESC", order.toOrderByStatement())
    }
}
