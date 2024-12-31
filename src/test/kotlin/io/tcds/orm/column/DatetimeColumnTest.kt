package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.time.Instant

class DatetimeColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val instant = Instant.now()
    private val column = DatetimeColumn<ColumnTypes>("foo") { it.instant }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "DATETIME", column.describe())

    @Test
    fun `given a date value when it is not null then set the value into the statement`() {
        every { stmt.setTimestamp(any(), any()) } just runs

        column.bind(stmt, 3, instant)

        verify(exactly = 1) { stmt.setTimestamp(3, Timestamp.from(instant)) }
    }
}
