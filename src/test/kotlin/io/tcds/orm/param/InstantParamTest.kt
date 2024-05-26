package io.tcds.orm.param

import io.mockk.mockk
import io.mockk.verify
import io.tcds.orm.extension.toInstant
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.time.LocalDateTime

class InstantParamTest {
    private val stmt: PreparedStatement = mockk(relaxed = true)
    private val value = LocalDateTime.of(2023, 10, 27, 15, 45, 55).toInstant()
    private val param = InstantParam("created_at", value)

    @Test
    fun `given a param when bind is invoked then bind its value into the statement`() {
        param.bind(stmt, 3)

        verify(exactly = 1) { stmt.setTimestamp(3, Timestamp.from(value)) }
    }

    @Test
    fun `given a param when describe is invoked then return the name equals to value`() {
        val string = param.describe()

        Assertions.assertEquals("created_at=2023-10-27T15:45:55Z", string)
    }
}
