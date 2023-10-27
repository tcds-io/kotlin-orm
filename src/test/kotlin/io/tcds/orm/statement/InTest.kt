package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.param.ColumnParam
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.valueIn
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class InTest {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `create infix in to statement and params`() {
        val values = listOf("123", "456")

        val clause = column valueIn values

        Assertions.assertEquals("name IN (?,?)", clause.toStmt())
        Assertions.assertEquals("name IN (`123`, `456`)", clause.toSql())
        Assertions.assertEquals(listOf(ColumnParam(column, "123"), ColumnParam(column, "456")), clause.params())
    }

    @Test
    fun `create fun in to statement and params`() {
        val values = listOf("123", "456")

        val clause = column.valueIn(values)

        Assertions.assertEquals("name IN (?,?)", clause.toStmt())
        Assertions.assertEquals("name IN (`123`, `456`)", clause.toSql())
        Assertions.assertEquals(listOf(ColumnParam(column, "123"), ColumnParam(column, "456")), clause.params())
    }
}
