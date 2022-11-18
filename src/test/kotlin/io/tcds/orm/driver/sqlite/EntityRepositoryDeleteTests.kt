package io.tcds.orm.driver.sqlite

import fixtures.Address
import fixtures.AddressTable
import io.tcds.orm.EntityRepository
import io.tcds.orm.Param
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import io.tcds.orm.statement.Order
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class EntityRepositoryDeleteTests : TestCase() {
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
    fun `given an entity when delete gets called then the entry gets deleted`() {
        val address = Address(
            id = "arthur-dent-address",
            street = "Galaxy Avenue",
            number = "124T",
            main = true,
            createdAt = LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
        )

        addressRepository.delete(address)

        Assertions.assertEquals(
            0,
            connection().query(
                table = addressTable,
                where = where(addressTable.id equalsTo "arthur-dent-address"),
                order = mapOf(addressTable.id to Order.ASC),
            ).count()
        )
    }
}