package io.tcds.orm.driver.sqlite

import fixtures.AddressTable
import io.tcds.orm.Param
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class RepositoryExistsTests : TestCase() {
    private val table = AddressTable(connection())

    @BeforeEach
    override fun setup() {
        super.setup()

        connection().execute(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                Param(table.id, "arthur-dent-address"),
                Param(table.street, "Galaxy Avenue"),
                Param(table.number, "124T"),
                Param(table.main, true),
                Param(table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )
    }

    @Test
    fun `given a condition when entry exists then exists returns true`() {
        val where = where(table.main equalsTo true)

        val exists = table.exists(where)

        Assertions.assertTrue(exists)
    }

    @Test
    fun `given a condition when entry does not exist then exists returns false`() {
        val where = where(table.main equalsTo false)

        val exists = table.exists(where)

        Assertions.assertFalse(exists)
    }
}
