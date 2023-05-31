package io.tcds.orm.column.nullable

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Types

class NullableJsonColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val column = NullableJsonColumn<ColumnTypes, ColumnTypes.Data>("foo") { it.json }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "JSON NULL", column.describe())

    @Test
    fun `given an object when it is not null then set a json string value into the statement`() {
        every { stmt.setString(any(), any()) } just runs

        column.bind(stmt, 3, ColumnTypes.Data(a = "AAA", b = 18))

        verify(exactly = 1) { stmt.setString(3, """{"a":"AAA","b":18}""") }
    }

    @Test
    fun `given an object when it is null then set null value into the statement`() {
        every { stmt.setNull(any(), any()) } just runs

        column.bind(stmt, 3, null)

        verify(exactly = 1) { stmt.setNull(3, Types.VARCHAR) }
    }
}
