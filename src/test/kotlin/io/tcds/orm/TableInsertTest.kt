package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.toInstant
import io.tcds.orm.param.ColumnParam
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
                    ColumnParam(table.id, address.id),
                    ColumnParam(table.street, address.street),
                    ColumnParam(table.number, address.number),
                    ColumnParam(table.main, address.main),
                    ColumnParam(table.createdAt, address.createdAt.toInstant()),
                ),
            )
        }
    }
}
