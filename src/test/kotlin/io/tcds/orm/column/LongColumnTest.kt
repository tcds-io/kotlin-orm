package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class LongColumnTest {
    private val stmt: PreparedStatement = mockk()

    @Test
    fun `given a long value when it is not null then set the value into the statement`() {
        every { stmt.setLong(any(), any()) } just runs
        val column = LongColumn<ColumnTypes>("long") { it.long }

        column.bind(stmt, 3, 16)

        verify(exactly = 1) { stmt.setLong(3, 16) }
    }
}
