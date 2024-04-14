package fixtures

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Table
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.*

@Suppress("MemberVisibilityCanBePrivate")
class CompanyTable(
    connection: Connection,
) : Table<Company>(
    connection = connection,
    table = "companies",
) {
    val id = varchar("id") { it.id }
    val name = varchar("name") { it.name }
    val status = enum("status") { it.status }
    val employees = integer("employees") { it.employees }
    val online = bool("online") { it.online }
    val createdAt = datetime("created_at") { it.createdAt.toDate() }

    override fun entry(row: OrmResultSet): Company = Company(
        id = row.get(id),
        name = row.get(name),
        status = row.get(status),
        employees = row.get(employees),
        online = row.get(online),
        createdAt = row.get(createdAt).toLocalDateTime(),
    )
}
