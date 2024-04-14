package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.time.LocalDateTime

class LocalDateTimeColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val datetime = LocalDateTime.now()
    private val column = LocalDateTimeColumn<ColumnTypes>("foo") { it.localdatetime }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "DATETIME", column.describe())

    @Test
    fun `given a datetime value when it is not null then set the value into the statement`() {
        every { stmt.setTimestamp(any(), any()) } just runs

        column.bind(stmt, 3, datetime)

        verify(exactly = 1) { stmt.setTimestamp(3, Timestamp.valueOf(datetime)) }
    }
}
