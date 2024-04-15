package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.valueNotIn
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NotInTest {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `create infix notIn to statement and params`() {
        val values = listOf("123", "456")

        val clause = column valueNotIn values

        Assertions.assertEquals("name NOT IN (?,?)", clause.toStmt())
        Assertions.assertEquals("name NOT IN (`123`, `456`)", clause.toSql())
        Assertions.assertEquals(listOf(ColumnParam(column, "123"), ColumnParam(column, "456")), clause.params())
    }

    @Test
    fun `create fun notIn to statement and params`() {
        val values = listOf("123", "456")

        val clause = column.valueNotIn(values)

        Assertions.assertEquals("name NOT IN (?,?)", clause.toStmt())
        Assertions.assertEquals("name NOT IN (`123`, `456`)", clause.toSql())
        Assertions.assertEquals(listOf(ColumnParam(column, "123"), ColumnParam(column, "456")), clause.params())
    }
}
