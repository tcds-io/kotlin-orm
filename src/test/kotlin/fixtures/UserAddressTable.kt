package fixtures

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Table
import io.tcds.orm.driver.Connection
import io.tcds.orm.extension.get

@Suppress("MemberVisibilityCanBePrivate")
class UserAddressTable(
    connection: Connection,
) : Table<UserAddress>(
    connection = connection,
    tableName = "user_address",
) {
    val userId = varchar("user_id") { it.userId }
    val address = json("address") { it.address }

    override fun entry(row: OrmResultSet): UserAddress {
        return UserAddress(
            userId = row.get(userId),
            address = row.get(address),
        )
    }
}
