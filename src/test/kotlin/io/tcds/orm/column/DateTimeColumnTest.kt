package io.tcds.orm.column

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.sql.Types
import java.time.LocalDateTime

class DateTimeColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val datetime = LocalDateTime.now()

    @Test
    fun `given a datetime value when it is not null then set the value into the statement`() {
        every { stmt.setTimestamp(any(), any()) } just runs
        val column = DateTimeColumn<ColumnTypes>("at") { it.datetime }

        column.bind(stmt, 3, datetime)

        verify(exactly = 1) { stmt.setTimestamp(3, Timestamp.valueOf(datetime)) }
    }

    @Test
    fun `given a datetime value when it is null then set null value into the statement`() {
        every { stmt.setNull(any(), any()) } just runs
        val column = DateTimeColumn<ColumnTypes>("at") { it.datetime }

        column.bind(stmt, 3, null)

        verify(exactly = 1) { stmt.setNull(3, Types.TIMESTAMP) }
    }
}
