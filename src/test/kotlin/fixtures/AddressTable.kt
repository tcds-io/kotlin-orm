package fixtures

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Table
import io.tcds.orm.connection.Connection

class AddressTable(
    connection: Connection,
    softDelete: Boolean = false,
) : Table<Address>(connection, "addresses", softDelete) {
    val id = varchar("id") { it.id }
    val street = varchar("street") { it.street }
    val number = varchar("number") { it.number }
    val main = bool("main") { it.main }
    val createdAt = datetime("created_at") { it.createdAt }

    override suspend fun entry(row: OrmResultSet): Address = Address(
        id = row.get(id),
        street = row.get(street),
        number = row.get(number),
        main = row.get(main),
        createdAt = row.get(createdAt),
    )
}
