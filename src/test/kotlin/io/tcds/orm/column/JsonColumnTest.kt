package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class JsonColumnTest {
    private val stmt: PreparedStatement = mockk()

    @Test
    fun `given an object when it is not null then set a json string value into the statement`() {
        every { stmt.setString(any(), any()) } just runs
        val column = JsonColumn<ColumnTypes, ColumnTypes.Data>("json") { it.json }

        column.bind(stmt, 3, ColumnTypes.Data(a = "AAA", b = 18))

        verify(exactly = 1) { stmt.setString(3, """{"a":"AAA","b":18}""") }
    }
}
