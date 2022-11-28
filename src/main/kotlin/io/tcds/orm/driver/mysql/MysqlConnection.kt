package io.tcds.orm.driver.mysql

import io.tcds.orm.driver.Connection
import org.slf4j.Logger
import java.sql.DriverManager
import java.sql.Connection as JdbcConnection

class MysqlConnection(
    jdbcReadUrl: String,
    jdbcReadWriteUrl: String,
    override val logger: Logger?,
) : Connection {
    override val readOnly: JdbcConnection = DriverManager.getConnection(jdbcReadUrl)
    override val readWrite: JdbcConnection = DriverManager.getConnection(jdbcReadWriteUrl)

    init {
        if (!readOnly.isValid(0)) throw Exception("Orm: Invalid ro connection")
        if (!readWrite.isValid(0)) throw Exception("Orm: Invalid rw connection")
    }
}
