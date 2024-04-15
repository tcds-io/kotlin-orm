package io.tcds.orm.column.nullable

import fixtures.ColumnTypes
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.sql.Types
import java.time.Instant

class NullableTimestampColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val instant = Instant.now()
    private val column = NullableTimestampColumn<ColumnTypes>("foo") { it.instant }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "DATETIME NULL", column.describe())

    @Test
    fun `given a date value when it is not null then set the value into the statement`() {
        every { stmt.setTimestamp(any(), any()) } just runs

        column.bind(stmt, 3, instant)

        verify(exactly = 1) { stmt.setTimestamp(3, Timestamp.from(instant)) }
    }

    @Test
    fun `given a date value when it is null then set null value into the statement`() {
        every { stmt.setNull(any(), any()) } just runs

        column.bind(stmt, 3, null)

        verify(exactly = 1) { stmt.setNull(3, Types.TIMESTAMP) }
    }
}
