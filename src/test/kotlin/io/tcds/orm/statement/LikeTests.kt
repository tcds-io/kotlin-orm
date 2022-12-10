package io.tcds.orm.statement

import io.tcds.orm.Param
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.like
import fixtures.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LikeTests {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `create infix like statement and params`() {
        val clause = column like "123"

        Assertions.assertEquals("name LIKE ?", clause.toStmt())
        Assertions.assertEquals(listOf(Param(column, "123")), clause.params())
    }

    @Test
    fun `create fun like statement and params`() {
        val clause = column.like("345")

        Assertions.assertEquals("name LIKE ?", clause.toStmt())
        Assertions.assertEquals(listOf(Param(column, "345")), clause.params())
    }
}
