package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.column.IntegerColumn
import io.tcds.orm.extension.isBetween
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BetweenTest {
    private val column = IntegerColumn<User>("age") { it.age }

    @Test
    fun `create between statement and params`() {
        val clause = column.isBetween(23, 44)

        Assertions.assertEquals("age BETWEEN ? AND ?", clause.toStmt())
        Assertions.assertEquals("age BETWEEN `23` AND `44`", clause.toSql())
        Assertions.assertEquals(listOf(ColumnParam(column, 23), ColumnParam(column, 44)), clause.params())
    }
}
