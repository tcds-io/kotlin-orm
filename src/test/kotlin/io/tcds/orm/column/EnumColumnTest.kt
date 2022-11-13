package io.tcds.orm.column

import fixtures.ColumnTypes
import fixtures.Status
import io.mockk.*
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Types

class EnumColumnTest {
    private val stmt: PreparedStatement = mockk()

    @Test
    fun `given a enum value when it is not null then set a string value into the statement`() {
        every { stmt.setString(any(), any()) } just runs
        val column = EnumColumn<ColumnTypes, Status>("enum") { it.enum }

        column.bind(stmt, 3, Status.ACTIVE)

        verify(exactly = 1) { stmt.setString(3, "ACTIVE") }
    }

    @Test
    fun `given an enum value when it is null then set null value into the statement`() {
        every { stmt.setNull(any(), any()) } just runs
        val column = EnumColumn<ColumnTypes, Status>("enum") { it.enum }

        column.bind(stmt, 3, null)

        verify(exactly = 1) { stmt.setNull(3, Types.VARCHAR) }
    }
}
