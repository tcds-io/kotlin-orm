package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class BooleanColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val column = BooleanColumn<ColumnTypes>("foo") { it.boolean }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "BOOLEAN", column.describe())

    @Test
    fun `given a boolean value when it is not null then set the value into the statement`() {
        every { stmt.setBoolean(any(), any()) } just runs

        column.bind(stmt, 3, false)

        verify(exactly = 1) { stmt.setBoolean(3, false) }
    }
}
