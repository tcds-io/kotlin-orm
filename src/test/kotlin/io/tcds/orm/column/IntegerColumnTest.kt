package io.tcds.orm.column

import fixtures.User
import io.mockk.*
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class IntegerColumnTest {
    @Test
    fun `when binding params are given then set into the statement `() {
        val stmt: PreparedStatement = mockk()
        every { stmt.setInt(any(), any()) } just runs

        val column = IntegerColumn<User>("age") { it.age }

        column.bind(stmt, 3, 16)
        verify(exactly = 1) { stmt.setInt(3, 16) }
    }
}
