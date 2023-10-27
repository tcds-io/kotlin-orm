package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.param.ColumnParam
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.isNotNull
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class IsNotNullTest {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `create is not null statement`() {
        val clause = column.isNotNull()

        Assertions.assertEquals("name IS NOT NULL", clause.toStmt())
        Assertions.assertEquals(emptyList<ColumnParam<User, String>>(), clause.params())
    }
}
