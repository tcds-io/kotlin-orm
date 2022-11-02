package fixtures

import io.tcds.orm.EntityRepository
import io.tcds.orm.EntityTable
import io.tcds.orm.OrmResultSet
import io.tcds.orm.column.StringColumn

class UserTable(
    private val addresses: EntityRepository<Address, String>,
) : EntityTable<User, String>(
    table = "users",
    id = StringColumn("id") { it.id },
) {
    private val name = varchar("name") { it.name }
    private val email = varchar("email") { it.email }
    private val height = float("height") { it.height }
    private val age = integer("age") { it.age }
    private val active = bool("active") { it.active }
    private val addressId = varchar("address_id") { it.address.id }

    override fun entity(row: OrmResultSet): User = User(
        id = row.get(id),
        name = row.get(name),
        email = row.get(email),
        height = row.get(height),
        age = row.get(age),
        active = row.get(active),
        address = addresses.loadById(row.get(addressId))!!,
    )
}
