package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.util.Date

class DateColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val datetime = Date()
    private val column = DateColumn<ColumnTypes>("foo") { it.date }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "DATE", column.describe())

    @Test
    fun `given a datetime value when it is not null then set the value into the statement`() {
        every { stmt.setTimestamp(any(), any()) } just runs

        column.bind(stmt, 3, datetime)

        verify(exactly = 1) { stmt.setTimestamp(3, Timestamp(datetime.time)) }
    }
}
