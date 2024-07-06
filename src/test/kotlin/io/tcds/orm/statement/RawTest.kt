package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.*
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RawTest {
    private val column = StringColumn<User>("name") { it.name }

    @Test
    fun `create where raw query statement and params`() {
        val params = column.toParam("Arthur")

        val clause = where("(SELECT name FROM users LIMIT 1) = ?", params)

        Assertions.assertEquals("WHERE (SELECT name FROM users LIMIT 1) = ?", clause.toStmt())
        Assertions.assertEquals("WHERE (SELECT name FROM users LIMIT 1) = `Arthur`", clause.toSql())
        Assertions.assertEquals(listOf(params), clause.params())
    }

    @Test
    fun `create and raw query statement and params`() {
        val clause = where(column equalsTo "Arthur")
            .and("(SELECT name FROM users LIMIT 1) = ?", column.toParam("John"))
            .or("(SELECT name FROM users LIMIT 1) = ?", column.toParam("Lennon"))

        Assertions.assertEquals(
            "WHERE name = ? AND (SELECT name FROM users LIMIT 1) = ? OR (SELECT name FROM users LIMIT 1) = ?",
            clause.toStmt(),
        )
        Assertions.assertEquals(
            "WHERE name = `Arthur` AND (SELECT name FROM users LIMIT 1) = `John` OR (SELECT name FROM users LIMIT 1) = `Lennon`",
            clause.toSql(),
        )
        Assertions.assertEquals(
            listOf(
                ColumnParam(column, "Arthur"),
                column.toParam("John"),
                column.toParam("Lennon"),
            ),
            clause.params(),
        )
    }

    @Test
    fun `create grouped raw query statement and params`() {
        val clause = where(column equalsTo "Arthur")
            .or {
                group("(SELECT a FROM aaa LIMIT 1) = ?", column.toParam("John"))
                    .and("(SELECT b FROM bbb LIMIT 1) = ?", column.toParam("Lennon"))
            }

        Assertions.assertEquals(
            "WHERE name = ? OR ((SELECT a FROM aaa LIMIT 1) = ? AND (SELECT b FROM bbb LIMIT 1) = ?)",
            clause.toStmt(),
        )
        Assertions.assertEquals(
            "WHERE name = `Arthur` OR ((SELECT a FROM aaa LIMIT 1) = `John` AND (SELECT b FROM bbb LIMIT 1) = `Lennon`)",
            clause.toSql(),
        )
        Assertions.assertEquals(
            listOf(
                ColumnParam(column, "Arthur"),
                column.toParam("John"),
                column.toParam("Lennon"),
            ),
            clause.params(),
        )
    }
}
