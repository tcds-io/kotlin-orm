package io.tcds.orm.connection.sqlite

import fixtures.AddressTable
import fixtures.coWrite
import io.tcds.orm.Param
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class TableExistsTest : SqLiteTestCase() {
    private val table = AddressTable(connection())

    @BeforeEach
    override fun setup() {
        super.setup()

        connection().coWrite(
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
    fun `given a condition when entry exists then exists returns true`() = runBlocking {
        val where = where(table.main equalsTo true)

        val exists = table.exists(where)

        Assertions.assertTrue(exists)
    }

    @Test
    fun `given a condition when entry does not exist then exists returns false`() = runBlocking {
        val where = where(table.main equalsTo false)

        val exists = table.exists(where)

        Assertions.assertFalse(exists)
    }
}
