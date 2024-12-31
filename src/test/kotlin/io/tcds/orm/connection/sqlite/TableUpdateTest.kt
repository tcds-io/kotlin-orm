package io.tcds.orm.connection.sqlite

import fixtures.Address
import fixtures.AddressTable
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class TableUpdateTest : SqLiteTestCase() {
    private val table = AddressTable(connection())

    private val address = Address(
        id = "arthur-dent-address-new-address-for-test",
        street = "Galaxy Avenue",
        number = "124T",
        main = true,
        createdAt = LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
    )

    @Test
    fun `given an entity when update gets called then the entry gets updated`() {
        table.insert(address)

        table.update(
            listOf(ColumnParam(table.street, "Galaxy Highway"), ColumnParam(table.number, "5432A")),
            where(table.id equalsTo "arthur-dent-address-new-address-for-test"),
        )

        Assertions.assertEquals(
            listOf(
                Address(
                    id = address.id,
                    street = "Galaxy Highway",
                    number = "5432A",
                    main = address.main,
                    createdAt = address.createdAt,
                ),
            ),
            connection().read(
                "SELECT * FROM addresses where id = ?",
                listOf(ColumnParam(table.id, "arthur-dent-address-new-address-for-test")),
            ).map { table.entry(it) }.toList(),
        )
    }
}
