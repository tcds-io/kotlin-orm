package io.tcds.orm.driver.sqlite

import fixtures.AddressTable
import io.tcds.orm.EntityRepository
import io.tcds.orm.Param
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class RepositoryLoadByQueryTests : TestCase() {
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

        connection().execute(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                Param(addressTable.id, "something-else"),
                Param(addressTable.street, "Galaxy Avenue"),
                Param(addressTable.number, "789A"),
                Param(addressTable.main, true),
                Param(addressTable.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )
    }

    @Test
    fun `given a sql query and ASC order when entry exists then load into the entity`() {
        val sql = "SELECT * FROM ${addressTable.tableName} ORDER BY id ASC LIMIT 1"

        val address = addressRepository.loadByQuery(sql)

        Assertions.assertEquals("arthur-dent-address", address?.id)
    }

    @Test
    fun `given a sql and a condition when entry exists then load into the entity`() {
        val sql = "SELECT * FROM ${addressTable.tableName} ORDER BY id DESC LIMIT 1"

        val address = addressRepository.loadByQuery(sql)

        Assertions.assertEquals("something-else", address?.id)
    }

    @Test
    fun `given a sql query and DESC order when entry exists then load into the entity`() {
        val sql = "SELECT * FROM ${addressTable.tableName} WHERE number = ? ORDER BY id DESC LIMIT 1"
        val params = listOf(Param(addressTable.number, "124T"))

        val address = addressRepository.loadByQuery(sql, params)

        Assertions.assertEquals("arthur-dent-address-another-address", address?.id)
    }
}
