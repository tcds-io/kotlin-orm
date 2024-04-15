package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StatementGroupTest {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `given a condition then group into parenthesis`() {
        val group = StatementGroup(conditions = mutableListOf(Pair(Operator.NONE, (column equalsTo "123"))))

        Assertions.assertEquals("(name = ?)", group.toStmt())
        Assertions.assertEquals("(name = `123`)", group.toSql())
        Assertions.assertEquals(listOf(ColumnParam(column, "123")), group.params())
    }
}
