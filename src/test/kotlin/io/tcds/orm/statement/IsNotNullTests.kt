package io.tcds.orm.statement

import io.tcds.orm.Param
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.isNotNull
import fixtures.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class IsNotNullTests {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `create is not null statement`() {
        val clause = column.isNotNull()

        Assertions.assertEquals("name IS NOT NULL", clause.toString())
        Assertions.assertEquals(emptyList<Param<User, String>>(), clause.params())
    }
}