package io.tcds.orm.column.nullable

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.Types
import java.time.LocalDate

class NullableLocalDateColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val date = LocalDate.now()
    private val column = NullableLocalDateColumn<ColumnTypes>("foo") { it.localdate }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "DATE NULL", column.describe())

    @Test
    fun `given a date value when it is not null then set the value into the statement`() {
        every { stmt.setDate(any(), any()) } just runs

        column.bind(stmt, 3, date)

        verify(exactly = 1) { stmt.setDate(3, Date.valueOf(date)) }
    }

    @Test
    fun `given a date value when it is null then set null value into the statement`() {
        every { stmt.setNull(any(), any()) } just runs

        column.bind(stmt, 3, null)

        verify(exactly = 1) { stmt.setNull(3, Types.DATE) }
    }
}
