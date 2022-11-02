package io.tcds.orm.column

import fixtures.User
import io.mockk.*
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class StringColumnTest {
    @Test
    fun `when binding params are given then set into the statement `() {
        val stmt: PreparedStatement = mockk()
        every { stmt.setString(any(), any()) } just runs

        val column = StringColumn<User>("name") { it.name }

        column.bind(stmt, 6, "Arthur Dent")
        verify(exactly = 1) { stmt.setString(6, "Arthur Dent") }
    }
}
