package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.like
import io.tcds.orm.param.StringParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LikeTest {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `create infix like statement and params`() {
        val clause = column like "123"

        Assertions.assertEquals("name LIKE ?", clause.toStmt())
        Assertions.assertEquals("name LIKE `123`", clause.toSql())
        Assertions.assertEquals(listOf(StringParam(column.name, "123")), clause.params())
    }

    @Test
    fun `create fun like statement and params`() {
        val clause = column.like("345")

        Assertions.assertEquals("name LIKE ?", clause.toStmt())
        Assertions.assertEquals("name LIKE `345`", clause.toSql())
        Assertions.assertEquals(listOf(StringParam(column.name, "345")), clause.params())
    }
}
