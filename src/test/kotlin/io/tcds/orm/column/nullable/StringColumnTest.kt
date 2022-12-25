package io.tcds.orm.column.nullable

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Types

class StringColumnTest {
    private val stmt: PreparedStatement = mockk()

    @Test
    fun `given a string value when it is not null then set the value into the statement`() {
        every { stmt.setString(any(), any()) } just runs
        val column = NullableStringColumn<ColumnTypes>("string") { it.string }

        column.bind(stmt, 6, "Arthur Dent")

        verify(exactly = 1) { stmt.setString(6, "Arthur Dent") }
    }

    @Test
    fun `given a string value when it is null then set null value into the statement`() {
        every { stmt.setNull(any(), any()) } just runs
        val column = NullableStringColumn<ColumnTypes>("string") { it.string }

        column.bind(stmt, 3, null)

        verify(exactly = 1) { stmt.setNull(3, Types.VARCHAR) }
    }
}
