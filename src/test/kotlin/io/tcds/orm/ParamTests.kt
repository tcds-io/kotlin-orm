package io.tcds.orm

import fixtures.Address
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class ParamTests {
    companion object {
        private const val STREET = "Galaxy Avenue"
    }

    private val column: Column<Address, String> = mockk()
    private val stmt: PreparedStatement = mockk()

    private val param = Param(column, STREET)

    @Test
    fun `given a param when bind is invoked then bind its value into the connection`() {
        every { column.bind(any(), any(), any()) } just runs

        param.bind(1, stmt)

        verify(exactly = 1) { column.bind(stmt, 1, STREET) }
    }

    @Test
    fun `given a param when toString is invoked then return the column equals to value string representation`() {
        every { column.name } returns "street"

        val string = param.toString()

        Assertions.assertEquals("street=$STREET", string)
    }
}
