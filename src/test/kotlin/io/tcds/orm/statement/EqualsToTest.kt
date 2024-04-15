package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EqualsToTest {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `create infix equals to statement and params`() {
        val clause = column equalsTo "123"

        Assertions.assertEquals("name = ?", clause.toStmt())
        Assertions.assertEquals("name = `123`", clause.toSql())
        Assertions.assertEquals(listOf(ColumnParam(column, "123")), clause.params())
    }

    @Test
    fun `create fun equals to statement and params`() {
        val clause = column.equalsTo("345")

        Assertions.assertEquals("name = ?", clause.toStmt())
        Assertions.assertEquals("name = `345`", clause.toSql())
        Assertions.assertEquals(listOf(ColumnParam(column, "345")), clause.params())
    }
}
