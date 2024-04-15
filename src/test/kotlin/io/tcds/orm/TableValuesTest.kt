package io.tcds.orm

import fixtures.Company
import fixtures.CompanyTable
import fixtures.Status
import fixtures.frozenClockAtApril
import io.mockk.mockk
import io.tcds.orm.connection.Connection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class TableValuesTest {
    private val connection: Connection = mockk()
    private val table = CompanyTable(connection)
    private val company = Company(
        id = "arthur-dent-co",
        name = "Arthur Dent Co",
        status = Status.ACTIVE,
        employees = 6,
        online = true,
        createdAt = LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
    )

    private val values = mapOf(
        "id" to "arthur-dent-co",
        "name" to "Arthur Dent Co",
        "status" to "ACTIVE",
        "employees" to 6,
        "online" to true,
        "created_at" to frozenClockAtApril,
    )

    @Test
    fun `given an entry then return its values`() = Assertions.assertEquals(values, table.values(company))

    @Test
    fun `given the values then return its entry`() = Assertions.assertEquals(company, table.entry(MapOrmResultSet(values)))
}
