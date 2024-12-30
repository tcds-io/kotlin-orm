package fixtures

import io.mockk.*
import io.tcds.orm.OrmResultSet
import io.tcds.orm.Table
import io.tcds.orm.extension.*

class ColumnTypesTable : Table<ColumnTypes>(connection = mockk(relaxed = true), table = "types") {
    private val long = long("long") { it.long }
    private val json = json("json") { it.json }
    private val enum = enum("enum") { it.enum }
    private val float = float("float") { it.float }
    private val integer = integer("integer") { it.integer }
    private val double = double("double") { it.double }
    private val string = varchar("string") { it.string }
    private val boolean = bool("boolean") { it.boolean }
    private val instant = datetime("instant") { it.instant }

    override fun entry(row: OrmResultSet): ColumnTypes = ColumnTypes(
        long = row.get(long),
        json = row.get(json),
        enum = row.get(enum),
        float = row.get(float),
        integer = row.get(integer),
        double = row.get(double),
        string = row.get(string),
        boolean = row.get(boolean),
        instant = row.get(instant),
    )
}
