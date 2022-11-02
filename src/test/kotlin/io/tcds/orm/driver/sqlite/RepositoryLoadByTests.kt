package io.tcds.orm.driver.sqlite

import fixtures.Address
import fixtures.AddressTable
import io.tcds.orm.Column
import io.tcds.orm.EntityRepository
import io.tcds.orm.Param
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.statement.Order
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class RepositoryLoadByTests : TestCase() {
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

        connection().execute(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                Param(addressTable.id, "arthur-dent-address-another-address"),
                Param(addressTable.street, "Galaxy Avenue"),
                Param(addressTable.number, "124T"),
                Param(addressTable.main, true),
                Param(addressTable.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )
    }

    @Test
    fun `given a condition and ASC order when entry exists then load into the entity`() {
        val conditions = listOf(addressTable.main equalsTo true)
        val order = mapOf<Column<Address, *>, Order>(addressTable.id to Order.ASC)

        val address = addressRepository.loadBy(conditions, order = order)

        Assertions.assertEquals("arthur-dent-address", address?.id)
    }

    @Test
    fun `given a condition and DESC order when entry exists then load into the entity`() {
        val conditions = listOf(addressTable.main equalsTo true)

        val address = addressRepository.loadBy(conditions, order = mapOf(addressTable.id to Order.DESC))

        Assertions.assertEquals("arthur-dent-address-another-address", address?.id)
    }
}
