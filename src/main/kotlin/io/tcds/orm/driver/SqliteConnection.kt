package io.tcds.orm.driver

import java.sql.DriverManager
import java.sql.Connection as JdbcConnection

class SqliteConnection(jdbcUrl: String) : Connection {
    override val readWrite: JdbcConnection = DriverManager.getConnection(jdbcUrl)
    override val readOnly: JdbcConnection = readWrite

    init {
        if (!readWrite.isValid(0)) throw Exception("Orm: Invalid rw connection")
    }
}
