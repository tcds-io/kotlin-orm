package io.tcds.orm.param.nullable

import io.mockk.mockk
import io.mockk.verify
import io.tcds.orm.extension.toInstant
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.sql.Types
import java.time.LocalDateTime

class NullableInstantParamTest {
    private val stmt: PreparedStatement = mockk(relaxed = true)
    private val value = LocalDateTime.of(2023, 10, 27, 15, 45, 59).toInstant()

    @Test
    fun `given a param when bind is invoked then bind its value into the statement`() {
        val param = NullableInstantParam("created_at", value)

        param.bind(stmt, 3)

        verify(exactly = 1) { stmt.setTimestamp(3, Timestamp.from(value)) }
    }

    @Test
    fun `given null value when bind is invoked then bind its value into the statement`() {
        val param = NullableInstantParam("created_at", null)

        param.bind(stmt, 1)

        verify(exactly = 1) { stmt.setNull(1, Types.TIMESTAMP) }
    }

    @Test
    fun `given a param when describe is invoked then return the name equals to value`() {
        val param = NullableInstantParam("created_at", value)

        val string = param.describe()

        Assertions.assertEquals("created_at=2023-10-27T15:45:59Z", string)
    }
}
