package io.tcds.orm.param

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement

class DoubleParamTest {
    private val stmt: PreparedStatement = mockk(relaxed = true)
    private val value = 100.0
    private val param = DoubleParam("amount", value)

    @Test
    fun `given a param when bind is invoked then bind its value into the statement`() {
        param.bind(1, stmt)

        verify(exactly = 1) { stmt.setDouble(1, value) }
    }

    @Test
    fun `given a param when describe is invoked then return the name equals to value`() {
        val string = param.describe()

        Assertions.assertEquals("amount=$value", string)
    }
}
