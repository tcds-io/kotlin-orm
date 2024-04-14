package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class JsonColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val column = JsonColumn<ColumnTypes, ColumnTypes.Json>("foo") { it.json }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "JSON", column.describe())

    @Test
    fun `given an object when it is not null then set a json string value into the statement`() {
        every { stmt.setString(any(), any()) } just runs

        column.bind(stmt, 3, ColumnTypes.Json(a = "AAA", b = 18))

        verify(exactly = 1) { stmt.setString(3, """{"a":"AAA","b":18}""") }
    }
}
