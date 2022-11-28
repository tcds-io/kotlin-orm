package io.tcds.orm.driver.pgsql

import io.tcds.orm.driver.Connection
import org.slf4j.Logger
import java.sql.DriverManager

class PgsqlConnection(
    jdbcReadUrl: String,
    jdbcReadWriteUrl: String,
    override val logger: Logger?,
) : Connection {
    override val readOnly: java.sql.Connection = DriverManager.getConnection(jdbcReadUrl)
    override val readWrite: java.sql.Connection = DriverManager.getConnection(jdbcReadWriteUrl)

    init {
        if (!readOnly.isValid(0)) throw Exception("Orm: Invalid ro connection")
        if (!readWrite.isValid(0)) throw Exception("Orm: Invalid rw connection")
    }
}
