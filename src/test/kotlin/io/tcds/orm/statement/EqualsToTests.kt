package io.tcds.orm.statement

import io.tcds.orm.Param
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.equalsTo
import fixtures.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EqualsToTests {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `create infix equals to statement and params`() {
        val clause = column equalsTo "123"

        Assertions.assertEquals("name = ?", clause.toString())
        Assertions.assertEquals(listOf(Param(column, "123")), clause.params())
    }

    @Test
    fun `create fun equals to statement and params`() {
        val clause = column.equalsTo("345")

        Assertions.assertEquals("name = ?", clause.toString())
        Assertions.assertEquals(listOf(Param(column, "345")), clause.params())
    }
}
