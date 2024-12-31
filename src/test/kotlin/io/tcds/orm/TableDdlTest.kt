package io.tcds.orm

import fixtures.ColumnTypesTable
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TableDdlTest {
    private val table = ColumnTypesTable()

    @Test
    fun `given a table object then generate table creation ddl`() {
        Assertions.assertEquals(
            """
            CREATE TABLE `types` (
                `long` BIGINT NOT NULL,
                `json` JSON NOT NULL,
                `enum` VARCHAR(255) NOT NULL,
                `float` FLOAT NOT NULL,
                `integer` INT NOT NULL,
                `double` DECIMAL(10, 2) NOT NULL,
                `string` VARCHAR(255) NOT NULL,
                `boolean` BOOLEAN NOT NULL,
                `instant` DATETIME(6) NOT NULL
            );
            """.trimIndent(),
            table.ddl(),
        )
    }
}
