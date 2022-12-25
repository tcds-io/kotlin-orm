package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class IntegerColumnTest {
    private val stmt: PreparedStatement = mockk()

    @Test
    fun `given an integer value when it is not null then set the value into the statement`() {
        every { stmt.setInt(any(), any()) } just runs
        val column = IntegerColumn<ColumnTypes>("integer") { it.integer }

        column.bind(stmt, 3, 16)

        verify(exactly = 1) { stmt.setInt(3, 16) }
    }
}
