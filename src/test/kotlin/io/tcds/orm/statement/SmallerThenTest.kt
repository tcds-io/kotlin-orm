package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.Param
import io.tcds.orm.column.IntegerColumn
import io.tcds.orm.extension.smallerThen
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SmallerThenTest {
    private val column = IntegerColumn<User>("age") { it.age }

    @Test
    fun `create infix smaller then statement and params`() {
        val clause = column smallerThen 30

        Assertions.assertEquals("age < ?", clause.toStmt())
        Assertions.assertEquals(listOf(Param(column, 30)), clause.params())
    }

    @Test
    fun `create fun smaller then statement and params`() {
        val clause = column.smallerThen(30)

        Assertions.assertEquals("age < ?", clause.toStmt())
        Assertions.assertEquals(listOf(Param(column, 30)), clause.params())
    }
}
