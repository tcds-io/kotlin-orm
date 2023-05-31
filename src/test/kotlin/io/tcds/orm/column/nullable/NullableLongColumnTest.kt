package io.tcds.orm.column.nullable

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Types

class NullableLongColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val column = NullableLongColumn<ColumnTypes>("foo") { it.long }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "LONG NULL", column.describe())

    @Test
    fun `given a long value when it is not null then set the value into the statement`() {
        every { stmt.setLong(any(), any()) } just runs

        column.bind(stmt, 3, 16)

        verify(exactly = 1) { stmt.setLong(3, 16) }
    }

    @Test
    fun `given a long value when it is null then set null value into the statement`() {
        every { stmt.setNull(any(), any()) } just runs

        column.bind(stmt, 3, null)

        verify(exactly = 1) { stmt.setNull(3, Types.BIGINT) }
    }
}
