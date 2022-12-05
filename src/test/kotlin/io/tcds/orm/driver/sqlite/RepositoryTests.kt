package io.tcds.orm.driver.sqlite

import fixtures.AddressTable
import io.tcds.orm.Param
import io.tcds.orm.Repository
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDateTime
import java.time.Month

class RepositoryTests : TestCase() {
    private val addressTable = AddressTable()
    private val addressRepository = Repository(addressTable, connection())

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
}
