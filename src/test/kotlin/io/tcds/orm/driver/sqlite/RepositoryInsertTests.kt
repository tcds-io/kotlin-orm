package io.tcds.orm.driver.sqlite

import fixtures.Address
import fixtures.AddressTable
import io.tcds.orm.EntityRepository
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.statement.Order
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class RepositoryInsertTests : TestCase() {
    private val addressTable = AddressTable()
    private val addressRepository = EntityRepository(addressTable, connection())

    @Test
    fun `given an entity when insert gets called then the entry gets inserted`() {
        val address = Address(
            id = "arthur-dent-address-new-address-for-test",
            street = "Galaxy Avenue",
            number = "124T",
            main = true,
            createdAt = LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
        )

        addressRepository.insert(address)

        Assertions.assertEquals(
            listOf(address),
            connection().select(
                table = addressTable.table,
                conditions = listOf(addressTable.id equalsTo "arthur-dent-address-new-address-for-test"),
                order = mapOf(addressTable.id to Order.ASC),
            ).map { addressTable.entity(it) }.toList()
        )
    }
}
