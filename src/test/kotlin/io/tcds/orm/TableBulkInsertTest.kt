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

class TableBulkInsertTest {
    private val connection: Connection = mockk()
    private val table = AddressTable(connection)

    private val first = Address.galaxyHighway()
    private val second = Address.galaxyAvenue()

    @Test
    fun `given the entry then invoke write in the connection`() {
        every { connection.write(any(), any()) } returns mockk()
        val entries = listOf(first, second)

        table.bulkInsert(entries)

        verify {
            connection.write(
                """
                    INSERT INTO addresses (id,street,number,main,created_at)
                        VALUES
                            (?,?,?,?,?),
                            (?,?,?,?,?)
                """.trimIndent(),
                listOf(
                    // first
                    StringParam(table.id.name, first.id),
                    StringParam(table.street.name, first.street),
                    StringParam(table.number.name, first.number),
                    BooleanParam(table.main.name, first.main),
                    InstantParam(table.createdAt.name, first.createdAt.toInstant()),
                    // second
                    StringParam(table.id.name, second.id),
                    StringParam(table.street.name, second.street),
                    StringParam(table.number.name, second.number),
                    BooleanParam(table.main.name, second.main),
                    InstantParam(table.createdAt.name, second.createdAt.toInstant()),
                ),
            )
        }
    }
}
