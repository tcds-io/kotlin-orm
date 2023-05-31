package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class IntegerColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val column = IntegerColumn<ColumnTypes>("foo") { it.integer }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "INTEGER", column.describe())

    @Test
    fun `given an integer value when it is not null then set the value into the statement`() {
        every { stmt.setInt(any(), any()) } just runs

        column.bind(stmt, 3, 16)

        verify(exactly = 1) { stmt.setInt(3, 16) }
    }
}
