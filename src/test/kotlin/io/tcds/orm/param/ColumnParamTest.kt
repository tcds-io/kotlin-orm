package io.tcds.orm.param

import fixtures.Address
import io.mockk.*
import io.tcds.orm.Column
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class ColumnParamTest {
    companion object {
        private const val STREET = "Galaxy Avenue"
    }

    private val column: Column<Address, String> = mockk()
    private val stmt: PreparedStatement = mockk()

    init {
        every { column.name } returns "street"
    }

    @Test
    fun `given a param when bind is invoked then bind its value into the statement`() {
        every { column.bind(any(), any(), any()) } just runs
        val param = ColumnParam(column, STREET)

        param.bind(1, stmt)

        verify(exactly = 1) { column.bind(stmt, 1, STREET) }
    }

    @Test
    fun `given a param when describe is invoked then return the name equals to value`() {
        val param = ColumnParam(column, STREET)

        val string = param.describe()

        Assertions.assertEquals("street=$STREET", string)
    }
}
