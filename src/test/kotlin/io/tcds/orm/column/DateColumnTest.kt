package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.Date
import java.sql.PreparedStatement
import java.time.LocalDate

class DateColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val date = LocalDate.now()
    private val column = DateColumn<ColumnTypes>("foo") { it.date }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "DATE", column.describe())

    @Test
    fun `given a date value when it is not null then set the value into the statement`() {
        every { stmt.setDate(any(), any()) } just runs

        column.bind(stmt, 3, date)

        verify(exactly = 1) { stmt.setDate(3, Date.valueOf(date)) }
    }
}
