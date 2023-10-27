package io.tcds.orm.param

import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.Date
import java.sql.PreparedStatement
import java.time.LocalDate

class DateParamTest {
    private val stmt: PreparedStatement = mockk(relaxed = true)
    private val value = LocalDate.of(2023, 10, 27)
    private val param = DateParam("created_at", value)

    @Test
    fun `given a param when bind is invoked then bind its value into the statement`() {
        param.bind(3, stmt)

        verify(exactly = 1) { stmt.setDate(3, Date.valueOf(value)) }
    }

    @Test
    fun `given a param when describe is invoked then return the name equals to value`() {
        val string = param.describe()

        Assertions.assertEquals("created_at=2023-10-27", string)
    }
}
