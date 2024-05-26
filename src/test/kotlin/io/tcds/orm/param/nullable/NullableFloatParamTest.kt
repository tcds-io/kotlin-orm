package io.tcds.orm.param.nullable

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Types

class NullableFloatParamTest {
    private val stmt: PreparedStatement = mockk(relaxed = true)
    private val value = 100.0.toFloat()

    @Test
    fun `given a param when bind is invoked then bind its value into the statement`() {
        val param = NullableFloatParam("amount", value)

        param.bind(stmt, 1)

        verify(exactly = 1) { stmt.setFloat(1, value) }
    }

    @Test
    fun `given null value when bind is invoked then bind its value into the statement`() {
        val param = NullableFloatParam("amount", null)

        param.bind(stmt, 1)

        verify(exactly = 1) { stmt.setNull(1, Types.FLOAT) }
    }

    @Test
    fun `given a param when describe is invoked then return the name equals to value`() {
        val param = NullableFloatParam("amount", value)

        val string = param.describe()

        Assertions.assertEquals("amount=$value", string)
    }
}
