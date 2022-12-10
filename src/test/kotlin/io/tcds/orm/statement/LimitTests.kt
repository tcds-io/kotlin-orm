package io.tcds.orm.statement

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LimitTests {
    @Test
    fun `when limit is null then string is empty`() {
        val limit = Limit(null, 10)

        Assertions.assertEquals("", limit.toStmt())
    }

    @Test
    fun `when limit is given and offset is null then string has limit`() {
        val limit = Limit(10, null)

        Assertions.assertEquals("LIMIT 10", limit.toStmt())
    }

    @Test
    fun `when limit and offset are given then string has limit and offset`() {
        val limit = Limit(10, 3)

        Assertions.assertEquals("LIMIT 10 OFFSET 3", limit.toStmt())
    }
}
