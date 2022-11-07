package fixtures

import io.tcds.orm.EntityTable
import io.tcds.orm.OrmResultSet
import io.tcds.orm.column.StringColumn

class AddressTable : EntityTable<Address, String>(
    table = "addresses",
    id = StringColumn("id") { it.id },
) {
    val street = varchar("street") { it.street }
    val number = varchar("number") { it.number }
    val main = bool("main") { it.main }
    val createdAt = datetime("created_at") { it.createdAt }

    override fun entry(row: OrmResultSet): Address = Address(
        id = row.get(id)!!,
        street = row.get(street)!!,
        number = row.get(number)!!,
        main = row.get(main)!!,
        createdAt = row.get(createdAt)!!,
    )
}
