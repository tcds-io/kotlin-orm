package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class DoubleColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val column = DoubleColumn<ColumnTypes>("foo") { it.double }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "DOUBLE", column.describe())

    @Test
    fun `given a double value when it is not null then set the value into the statement`() {
        every { stmt.setDouble(any(), any()) } just runs

        column.bind(stmt, 3, 100.77)

        verify(exactly = 1) { stmt.setDouble(3, 100.77) }
    }
}
