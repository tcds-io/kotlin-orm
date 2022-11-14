package io.tcds.orm.driver.sqlite

import fixtures.AddressTable
import io.tcds.orm.EntityRepository
import io.tcds.orm.Param
import io.tcds.orm.extension.emptyWhere
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import io.tcds.orm.statement.Order
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class RepositoryDeleteTests : TestCase() {
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
                Param(addressTable.id, "another-arthur-dent-address"),
                Param(addressTable.street, "Galaxy Avenue 2"),
                Param(addressTable.number, "555T"),
                Param(addressTable.main, true),
                Param(addressTable.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )
    }

    @Test
    fun `given an entity when delete gets called then the entry gets deleted`() {
        val where = where(addressTable.main equalsTo true)

        addressRepository.delete(where)

        Assertions.assertEquals(
            0,
            connection().query(
                table = addressTable,
                where = emptyWhere(),
                order = mapOf(addressTable.id to Order.ASC),
            ).count()
        )
    }
}
