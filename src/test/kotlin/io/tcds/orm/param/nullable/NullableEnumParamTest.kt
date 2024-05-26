package io.tcds.orm.param.nullable

import fixtures.Status
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Types

class NullableEnumParamTest {
    private val stmt: PreparedStatement = mockk(relaxed = true)
    private val value = Status.ACTIVE

    @Test
    fun `given a param when bind is invoked then bind its value into the statement`() {
        val param = NullableEnumParam("status", value)

        param.bind(stmt, 1)

        verify(exactly = 1) { stmt.setString(1, value.name) }
    }

    @Test
    fun `given null value when bind is invoked then bind its value into the statement`() {
        val param = NullableEnumParam("status", null)

        param.bind(stmt, 1)

        verify(exactly = 1) { stmt.setNull(1, Types.VARCHAR) }
    }

    @Test
    fun `given a param when describe is invoked then return the name equals to value`() {
        val param = NullableEnumParam("status", value)

        val string = param.describe()

        Assertions.assertEquals("status=${value.name}", string)
    }
}
