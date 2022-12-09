package io.tcds.orm.driver

import org.slf4j.Logger
import java.sql.DriverManager
import java.sql.Connection as JdbcConnection

class MysqlConnection(
    jdbcReadUrl: String,
    jdbcReadWriteUrl: String,
    override val logger: Logger?,
) : NestedTransactionConnection(jdbcReadUrl, jdbcReadWriteUrl, logger)
