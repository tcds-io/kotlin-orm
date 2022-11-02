package io.tcds.orm.extension

import fixtures.User
import io.tcds.orm.Param
import io.tcds.orm.column.IntegerColumn
import io.tcds.orm.column.StringColumn
import io.tcds.orm.statement.Condition
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class WhereTests {
    private val name = StringColumn<User>("name") { it.name }
    private val age = IntegerColumn<User>("age") { it.age }

    @Test
    fun `when order maps is empty then order statement is empty`() {
        val where = emptyList<Condition>()

        Assertions.assertEquals("", where.toWhereStatement())
        Assertions.assertEquals(emptyList<Param<User, *>>(), where.toParams())
    }

    @Test
    fun `when order maps is not empty then order statement has column order configuration`() {
        val where = listOf(
            age equalsTo 123,
            name like "Arthur Dent"
        )

        Assertions.assertEquals("WHERE age = ? AND name LIKE ?", where.toWhereStatement())
        Assertions.assertEquals(listOf(Param(age, 123), Param(name, "Arthur Dent")), where.toParams())
    }
}
