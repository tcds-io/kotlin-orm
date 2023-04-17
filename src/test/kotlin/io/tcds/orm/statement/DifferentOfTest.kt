package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.Param
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.differentOf
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DifferentOfTest {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `create infix different of statement and params`() {
        val clause = column differentOf "123"

        Assertions.assertEquals("name != ?", clause.toStmt())
        Assertions.assertEquals("name != `123`", clause.toSql())
        Assertions.assertEquals(listOf(Param(column, "123")), clause.params())
    }

    @Test
    fun `create fun different of statement and params`() {
        val clause = column.differentOf("345")

        Assertions.assertEquals("name != ?", clause.toStmt())
        Assertions.assertEquals("name != `345`", clause.toSql())
        Assertions.assertEquals(listOf(Param(column, "345")), clause.params())
    }
}
