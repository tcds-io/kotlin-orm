package fixtures

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Table
import io.tcds.orm.driver.Connection
import io.tcds.orm.extension.get

@Suppress("MemberVisibilityCanBePrivate")
class UserStatusTable(connection: Connection) : Table<UserStatus>(connection, tableName = "user_status") {
    val userId = varchar("user_id") { it.userId }
    val status = enum("status") { it.status }
    val at = datetime("at") { it.at }

    override fun entry(row: OrmResultSet) = UserStatus(
        userId = row.get(userId),
        status = row.get(status),
        at = row.get(at),
    )
}
