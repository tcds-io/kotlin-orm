package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import io.mockk.*
import io.tcds.orm.connection.Connection
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class TableInsertTest {
    private val connection: Connection = mockk()
    private val table = AddressTable(connection)

    private val address = Address.galaxyHighway()

    @Test
    fun `given the entry then invoke write in the connection`() {
        every { connection.write(any(), any()) } returns mockk()

        runBlocking { table.insert(address) }

        verify {
            connection.write(
                "INSERT INTO addresses (id, street, number, main, created_at) VALUES (?, ?, ?, ?, ?)",
                listOf(
                    Param(table.id, address.id),
                    Param(table.street, address.street),
                    Param(table.number, address.number),
                    Param(table.main, address.main),
                    Param(table.createdAt, address.createdAt),
                ),
            )
        }
    }
}
