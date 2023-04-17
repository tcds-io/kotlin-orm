package io.tcds.orm.connection.sqlite

import fixtures.Address
import fixtures.UserAddress
import fixtures.UserAddressTable
import io.tcds.orm.extension.emptyWhere
import io.tcds.orm.statement.Order
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class JsonColumnTest : SqLiteTestCase() {
    private val table = UserAddressTable(connection())
    private val address = Address(
        id = "arthur-dent-address",
        street = "Galaxy Avenue",
        number = "124T",
        main = true,
        createdAt = LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
    )

    @Test
    fun `given an address when table has json column then insert as json`() = runBlocking {
        table.insert(UserAddress("user-aaa", address), UserAddress("user-bbb", address))

        val list = table.findBy(emptyWhere(), listOf(table.userId.desc()))

        Assertions.assertEquals(
            listOf(
                UserAddress("user-bbb", address),
                UserAddress("user-aaa", address),
            ),
            list.toList(),
        )
    }
}
