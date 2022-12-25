package fixtures

import io.tcds.orm.EntityTable
import io.tcds.orm.OrmResultSet
import io.tcds.orm.column.StringColumn
import io.tcds.orm.driver.Connection
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.get
import io.tcds.orm.extension.nullable
import io.tcds.orm.extension.where
import io.tcds.orm.statement.Order

@Suppress("MemberVisibilityCanBePrivate")
class UserTable(
    connection: Connection,
    private val addressTable: AddressTable,
    private val statusTable: UserStatusTable,
) : EntityTable<User, String>(
    connection = connection,
    tableName = "users",
    id = StringColumn("id") { it.id },
) {
    val name = varchar("name") { it.name }
    val email = nullableVarchar("email") { it.email }
    val height = nullableFloat("height") { it.height }
    val age = integer("age") { it.age }
    val active = bool("active") { it.active }
    val addressId = varchar("address_id") { it.address.id }

    override fun entry(row: OrmResultSet): User {
        val userId = row.get(id)

        return User(
            id = userId,
            name = row.get(name),
            email = row.nullable(email),
            height = row.nullable(height),
            age = row.get(age),
            active = row.get(active),
            address = addressTable.loadById(row.get(addressId))!!,
            status = statusTable.select(
                where(statusTable.userId equalsTo userId),
                mapOf(statusTable.at to Order.ASC)
            ).toList()
        )
    }
}
