package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.isNull
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class IsNullTest {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `create is null statement`() {
        val clause = column.isNull()

        Assertions.assertEquals("name IS NULL", clause.toStmt())
        Assertions.assertEquals(emptyList<ColumnParam<User, String>>(), clause.params())
    }
}
