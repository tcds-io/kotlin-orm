package io.tcds.orm

import fixtures.Address
import fixtures.AddressTable
import io.mockk.*
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.toInstant
import io.tcds.orm.param.BooleanParam
import io.tcds.orm.param.InstantParam
import io.tcds.orm.param.StringParam
import org.junit.jupiter.api.Test

class TableInsertTest {
    private val connection: Connection = mockk()
    private val table = AddressTable(connection)

    private val address = Address.galaxyHighway()

    @Test
    fun `given the entry then invoke write in the connection`() {
        every { connection.write(any(), any()) } returns mockk()

        table.insert(address)

        verify {
            connection.write(
                """
                    INSERT INTO addresses (id,street,number,main,created_at)
                    VALUES
                    (?,?,?,?,?)
                """.trimIndent(),
                listOf(
                    StringParam(table.id.name, address.id),
                    StringParam(table.street.name, address.street),
                    StringParam(table.number.name, address.number),
                    BooleanParam(table.main.name, address.main),
                    InstantParam(table.createdAt.name, address.createdAt.toInstant()),
                ),
            )
        }
    }
}
