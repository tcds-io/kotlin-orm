package io.tcds.orm.driver

import org.slf4j.Logger
import java.sql.DriverManager
import java.sql.Connection as JdbcConnection

class SqliteConnection(
    jdbcUrl: String,
    override val logger: Logger?,
) : Connection {
    override val readWrite: JdbcConnection = DriverManager.getConnection(jdbcUrl)
    override val readOnly: JdbcConnection = readWrite

    init {
        if (!readWrite.isValid(0)) throw Exception("Orm: Invalid rw connection")
    }
}
