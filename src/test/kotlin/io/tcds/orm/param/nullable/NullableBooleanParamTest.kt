package io.tcds.orm.param.nullable

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Types

class NullableBooleanParamTest {
    private val stmt: PreparedStatement = mockk(relaxed = true)
    private val value = true

    @Test
    fun `given a param when bind is invoked then bind its value into the statement`() {
        val param = NullableBooleanParam("active", value)

        param.bind(stmt, 1)

        verify(exactly = 1) { stmt.setBoolean(1, value) }
    }

    @Test
    fun `given null value when bind is invoked then bind its value into the statement`() {
        val param = NullableBooleanParam("active", null)

        param.bind(stmt, 1)

        verify(exactly = 1) { stmt.setNull(1, Types.BOOLEAN) }
    }

    @Test
    fun `given a param when describe is invoked then return the name equals to value`() {
        val param = NullableBooleanParam("active", value)

        val string = param.describe()

        Assertions.assertEquals("active=$value", string)
    }
}
