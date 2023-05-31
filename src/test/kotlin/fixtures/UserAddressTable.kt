package fixtures

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Table
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.get
import io.tcds.orm.extension.json
import io.tcds.orm.extension.varchar

@Suppress("MemberVisibilityCanBePrivate")
class UserAddressTable(
    connection: Connection,
) : Table<UserAddress>(
    connection = connection,
    table = "user_address",
) {
    val userId = varchar("user_id") { it.userId }
    val address = json("address") { it.address }

    override fun entry(row: OrmResultSet): UserAddress = UserAddress(
        userId = row.get(userId),
        address = row.get(address),
    )
}
