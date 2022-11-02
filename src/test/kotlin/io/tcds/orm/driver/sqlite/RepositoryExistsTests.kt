package io.tcds.orm.driver.sqlite

import fixtures.AddressTable
import io.tcds.orm.EntityRepository
import io.tcds.orm.Param
import io.tcds.orm.extension.equalsTo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class RepositoryExistsTests : TestCase() {
    private val addressTable = AddressTable()
    private val addressRepository = EntityRepository(addressTable, connection())

    @BeforeEach
    override fun setup() {
        super.setup()

        connection().execute(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                Param(addressTable.id, "arthur-dent-address"),
                Param(addressTable.street, "Galaxy Avenue"),
                Param(addressTable.number, "124T"),
                Param(addressTable.main, true),
                Param(addressTable.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )
    }

    @Test
    fun `given a condition when entry exists then exists returns true`() {
        val conditions = listOf(addressTable.main equalsTo true)

        val exists = addressRepository.exists(conditions)

        Assertions.assertTrue(exists)
    }

    @Test
    fun `given a condition when entry does not exist then exists returns false`() {
        val conditions = listOf(addressTable.main equalsTo false)

        val exists = addressRepository.exists(conditions)

        Assertions.assertFalse(exists)
    }
}
