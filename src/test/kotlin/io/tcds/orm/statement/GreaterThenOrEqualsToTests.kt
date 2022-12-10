package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.Param
import io.tcds.orm.column.IntegerColumn
import io.tcds.orm.extension.graterThenOrEqualsTo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GreaterThenOrEqualsToTests {
    private val column = IntegerColumn<User>("age") { it.age }

    @Test
    fun `create infix grater then or equals to statement and params`() {
        val clause = column graterThenOrEqualsTo 30

        Assertions.assertEquals("age >= ?", clause.toStmt())
        Assertions.assertEquals(listOf(Param(column, 30)), clause.params())
    }

    @Test
    fun `create fun grater then or equals to statement and params`() {
        val clause = column.graterThenOrEqualsTo(30)

        Assertions.assertEquals("age >= ?", clause.toStmt())
        Assertions.assertEquals(listOf(Param(column, 30)), clause.params())
    }
}
