package fixtures

import io.tcds.orm.EntityTable
import io.tcds.orm.OrmResultSet
import io.tcds.orm.column.StringColumn

class AddressTable : EntityTable<Address, String>(
    table = "addresses",
    id = StringColumn("id") { it.id },
) {
    private val street = varchar("street") { it.street }
    private val number = varchar("number") { it.number }
    private val main = bool("main") { it.main }
    private val createdAt = datetime("created_at") { it.createdAt }
    private val deletedAt = datetime("deleted_at") { it.deletedAt }

    override fun entity(row: OrmResultSet): Address = Address(
        id = row.get(id),
        street = row.get(street),
        number = row.get(number),
        main = row.get(main),
        createdAt = row.get(createdAt)!!,
        deletedAt = row.get(deletedAt),
    )
}
