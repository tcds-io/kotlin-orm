package io.tcds.orm.driver.sqlite

import fixtures.Address
import fixtures.AddressTable
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import io.tcds.orm.statement.Order
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class RepositoryUpdateTests : TestCase() {
    private val table = AddressTable(connection())
    private val address = Address(
        id = "arthur-dent-address-new-address-for-test",
        street = "Galaxy Avenue",
        number = "124T",
        main = true,
        createdAt = LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
    )

    @Test
    fun `given an entity when update gets called then the entry gets updated`() {
        table.insert(address)
        val updated = address.updated(street = "Galaxy Highway", number = "5432A", main = false)

        table.update(updated)

        Assertions.assertEquals(
            listOf(
                Address(
                    id = address.id,
                    street = "Galaxy Highway",
                    number = "5432A",
                    main = false,
                    createdAt = address.createdAt,
                )
            ),
            connection().query(
                table = table,
                where = where(table.id equalsTo "arthur-dent-address-new-address-for-test"),
                order = mapOf(table.id to Order.ASC),
            ).map { table.entry(it) }.toList()
        )
    }

    @Test
    fun `given an entity when update gets called with specific columns then the entry gets updated`() {
        table.insert(address)
        val updated = address.updated(street = "Galaxy Highway", number = "5432A", main = false)

        table.update(updated, listOf(table.street))

        Assertions.assertEquals(
            listOf(
                Address(
                    id = address.id,
                    street = "Galaxy Highway",
                    number = address.number,
                    main = address.main,
                    createdAt = address.createdAt,
                )
            ),
            connection().query(
                table = table,
                where = where(table.id equalsTo "arthur-dent-address-new-address-for-test"),
                order = mapOf(table.id to Order.ASC),
            ).map { table.entry(it) }.toList()
        )
    }
}
