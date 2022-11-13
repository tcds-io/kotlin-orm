package fixtures

import io.tcds.orm.EntityRepository
import io.tcds.orm.EntityTable
import io.tcds.orm.OrmResultSet
import io.tcds.orm.Repository
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.get
import io.tcds.orm.extension.where
import io.tcds.orm.statement.Order

class UserTable(
    private val addresses: EntityRepository<Address, String>,
    private val statusList: Repository<UserStatus>,
) : EntityTable<User, String>(
    table = "users",
    id = StringColumn("id") { it.id },
) {
    private val statusTable = UserStatusTable()

    val name = varchar("name") { it.name }
    val email = varchar("email") { it.email }
    val height = float("height") { it.height }
    val age = integer("age") { it.age }
    val active = bool("active") { it.active }
    val addressId = varchar("address_id") { it.address.id }

    override fun entry(row: OrmResultSet): User {
        val userId = row.get(id)!!
        return User(
            id = userId,
            name = row.get(name)!!,
            email = row.get(email)!!,
            height = row.get(height)!!,
            age = row.get(age)!!,
            active = row.get(active)!!,
            address = addresses.loadById(row.get(addressId)!!)!!,
            status = statusList.select(
                where(statusTable.userId equalsTo userId),
                mapOf(statusTable.at to Order.ASC)
            ).toList()
        )
    }
}
