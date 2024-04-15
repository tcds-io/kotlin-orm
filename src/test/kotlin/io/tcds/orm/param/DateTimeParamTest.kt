package io.tcds.orm.param

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.time.LocalDateTime

class DateTimeParamTest {
    private val stmt: PreparedStatement = mockk(relaxed = true)
    private val value = LocalDateTime.of(2023, 10, 27, 10, 17, 47, 109511000)
    private val param = DateTimeParam("created_at", value)

    @Test
    fun `given a param when bind is invoked then bind its value into the statement`() {
        param.bind(3, stmt)

        verify(exactly = 1) { stmt.setTimestamp(3, Timestamp.valueOf(value)) }
    }

    @Test
    fun `given a param when describe is invoked then return the name equals to value`() {
        val string = param.describe()

        Assertions.assertEquals("created_at=2023-10-27T10:17:47.109511", string)
    }
}
