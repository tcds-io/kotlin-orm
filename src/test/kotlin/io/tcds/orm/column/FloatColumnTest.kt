package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Types

class FloatColumnTest {
    private val stmt: PreparedStatement = mockk()

    @Test
    fun `given a float value when it is not null then set the value into the statement`() {
        every { stmt.setFloat(any(), any()) } just runs
        val column = FloatColumn<ColumnTypes>("float") { it.float }

        column.bind(stmt, 3, 100.77.toFloat())

        verify(exactly = 1) { stmt.setFloat(3, 100.77.toFloat()) }
    }

    @Test
    fun `given a float value when it is null then set null value into the statement`() {
        every { stmt.setNull(any(), any()) } just runs
        val column = FloatColumn<ColumnTypes>("float") { it.float }

        column.bind(stmt, 3, null)

        verify(exactly = 1) { stmt.setNull(3, Types.FLOAT) }
    }
}
