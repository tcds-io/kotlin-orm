package io.tcds.orm.column

import fixtures.ColumnTypes
import fixtures.Status
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class EnumColumnTest {
    private val stmt: PreparedStatement = mockk()
    private val column = EnumColumn<ColumnTypes, Status>("foo") { it.enum }

    @Test
    fun `given a column then describe its configuration`() = Assertions.assertEquals("foo" to "ENUM", column.describe())

    @Test
    fun `given a enum value when it is not null then set a string value into the statement`() {
        every { stmt.setString(any(), any()) } just runs

        column.bind(stmt, 3, Status.ACTIVE)

        verify(exactly = 1) { stmt.setString(3, "ACTIVE") }
    }

    @Test
    fun `given another enum value when it is not null then set a string value into the statement`() {
        every { stmt.setString(any(), any()) } just runs

        column.bind(stmt, 3, Status.INACTIVE)

        verify(exactly = 1) { stmt.setString(3, "INACTIVE") }
    }
}
