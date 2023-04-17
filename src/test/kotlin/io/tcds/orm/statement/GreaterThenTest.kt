package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.Param
import io.tcds.orm.column.IntegerColumn
import io.tcds.orm.extension.greaterThen
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GreaterThenTest {
    private val column = IntegerColumn<User>("age") { it.age }

    @Test
    fun `create infix grater then statement and params`() {
        val clause = column greaterThen 30

        Assertions.assertEquals("age > ?", clause.toStmt())
        Assertions.assertEquals("age > `30`", clause.toSql())
        Assertions.assertEquals(listOf(Param(column, 30)), clause.params())
    }

    @Test
    fun `create fun grater then statement and params`() {
        val clause = column.greaterThen(30)

        Assertions.assertEquals("age > ?", clause.toStmt())
        Assertions.assertEquals("age > `30`", clause.toSql())
        Assertions.assertEquals(listOf(Param(column, 30)), clause.params())
    }
}
