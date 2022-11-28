package io.tcds.orm.driver.sqlite

import io.tcds.orm.driver.Connection
import org.slf4j.Logger
import java.sql.DriverManager

class SqliteConnection(
    jdbcUrl: String,
    override val logger: Logger?,
) : Connection {
    override val readWrite: java.sql.Connection = DriverManager.getConnection(jdbcUrl)
    override val readOnly: java.sql.Connection = readWrite

    init {
        if (!readWrite.isValid(0)) throw Exception("Orm: Invalid rw connection")
    }
}
