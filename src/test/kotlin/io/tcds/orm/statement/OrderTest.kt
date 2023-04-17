package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.column.IntegerColumn
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.OrderStatement
import io.tcds.orm.extension.toOrderByStatement
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OrderTest {
    @Test
    fun `when order maps is empty then order statement is empty`() {
        val order: OrderStatement<User> = emptyList()

        Assertions.assertEquals("", order.toOrderByStatement())
    }

    @Test
    fun `when order maps is not empty then order statement has column order configuration`() {
        val age = IntegerColumn<User>("age") { it.age }
        val name = StringColumn<User>("name") { it.name }

        val order = listOf(age.asc(), name.desc())

        Assertions.assertEquals("ORDER BY age ASC, name DESC", order.toOrderByStatement())
    }
}
