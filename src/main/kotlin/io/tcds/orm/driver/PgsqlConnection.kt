package io.tcds.orm.driver

import org.slf4j.Logger
import java.sql.DriverManager
import java.sql.Connection as JdbcConnection

class PgsqlConnection(
    jdbcReadUrl: String,
    jdbcReadWriteUrl: String,
    override val logger: Logger?,
) : NestedTransactionConnection(
    readOnly = DriverManager.getConnection(jdbcReadUrl),
    readWrite = DriverManager.getConnection(jdbcReadWriteUrl),
    logger = logger,
)
