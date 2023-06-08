package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import io.mockk.mockk
import io.tcds.orm.connection.Connection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class TableValuesTest {
    private val connection: Connection = mockk()
    private val table = AddressTable(connection)

    @Test
    fun `given an entry then return its values`() {
        val address = Address.galaxyHighway()

        val values = table.values(address)

        Assertions.assertEquals(
            mapOf(
                "id" to "galaxy-highway",
                "street" to "Galaxy Highway",
                "number" to "678H",
                "main" to false,
                "created_at" to LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
            ),
            values,
        )
    }
}
