package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class BooleanColumnTest {
    private val stmt: PreparedStatement = mockk()

    @Test
    fun `given a boolean value when it is not null then set the value into the statement`() {
        every { stmt.setBoolean(any(), any()) } just runs
        val column = BooleanColumn<ColumnTypes>("boolean") { it.boolean }

        column.bind(stmt, 3, false)

        verify(exactly = 1) { stmt.setBoolean(3, false) }
    }
}
