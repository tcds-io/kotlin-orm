package io.tcds.orm.column

import fixtures.ColumnTypes
import fixtures.Status
import io.mockk.*
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class EnumColumnTest {
    private val stmt: PreparedStatement = mockk()

    @Test
    fun `given a enum value when it is not null then set a string value into the statement`() {
        every { stmt.setString(any(), any()) } just runs
        val column = EnumColumn<ColumnTypes, Status>("enum") { it.enum }

        column.bind(stmt, 3, Status.ACTIVE)

        verify(exactly = 1) { stmt.setString(3, "ACTIVE") }
    }
}
