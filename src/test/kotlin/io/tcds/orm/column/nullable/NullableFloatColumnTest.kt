package io.tcds.orm.column.nullable

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Types

class NullableFloatColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val column = NullableFloatColumn<ColumnTypes>("foo") { it.float }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "FLOAT NULL", column.describe())

    @Test
    fun `given a float value when it is not null then set the value into the statement`() {
        every { stmt.setFloat(any(), any()) } just runs

        column.bind(stmt, 3, 100.77.toFloat())

        verify(exactly = 1) { stmt.setFloat(3, 100.77.toFloat()) }
    }

    @Test
    fun `given a float value when it is null then set null value into the statement`() {
        every { stmt.setNull(any(), any()) } just runs

        column.bind(stmt, 3, null)

        verify(exactly = 1) { stmt.setNull(3, Types.FLOAT) }
    }
}
