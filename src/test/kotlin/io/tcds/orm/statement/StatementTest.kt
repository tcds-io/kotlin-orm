package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.column.IntegerColumn
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.*
import io.tcds.orm.param.ColumnParam
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StatementTest {
    private val name = StringColumn<User>("name") { it.name }
    private val age = IntegerColumn<User>("age") { it.age }

    @Test
    fun `given where conditions then create where statement with params`() {
        val where = where(age equalsTo 32) or (age greaterThen 50) and (name differentOf "Arthur Dent")

        Assertions.assertEquals("WHERE age = ? OR age > ? AND name != ?", where.toStmt())
        Assertions.assertEquals("WHERE age = `32` OR age > `50` AND name != `Arthur Dent`", where.toSql())
        Assertions.assertEquals(
            listOf(ColumnParam(age, 32), ColumnParam(age, 50), ColumnParam(name, "Arthur Dent")),
            where.params(),
        )
    }

    @Test
    fun `given where and group conditions then create where statement with params`() {
        val where = where(age equalsTo 32) or {
            stmt(age greaterThen 50) and (name differentOf "Arthur Dent")
        } and {
            stmt(age smallerThen 100) and (name differentOf "Ford Prefect")
        }

        Assertions.assertEquals(
            "WHERE age = ? OR (age > ? AND name != ?) AND (age < ? AND name != ?)",
            where.toStmt(),
        )
        Assertions.assertEquals(
            "WHERE age = `32` OR (age > `50` AND name != `Arthur Dent`) AND (age < `100` AND name != `Ford Prefect`)",
            where.toSql(),
        )
        Assertions.assertEquals(
            listOf(
                ColumnParam(age, 32),
                ColumnParam(age, 50),
                ColumnParam(name, "Arthur Dent"),
                ColumnParam(age, 100),
                ColumnParam(name, "Ford Prefect"),
            ),
            where.params(),
        )
    }

    @Test
    fun `given where and group with empty where then create the query statement`() {
        val where = where(age equalsTo 32) and { stmt ->
            stmt.add(age greaterThen 50) or (age smallerThen 100) or {
                stmt(age greaterThen 50) or (age smallerThen 100)
            }
        } or { stmt ->
            stmt.add(name equalsTo "Arthur") and (name differentOf "Prefect")
        }

        Assertions.assertEquals(
            "WHERE age = ? AND (age > ? OR age < ? OR (age > ? OR age < ?)) OR (name = ? AND name != ?)",
            where.toStmt(),
        )
        Assertions.assertEquals(
            "WHERE age = `32` AND (age > `50` OR age < `100` OR (age > `50` OR age < `100`)) OR (name = `Arthur` AND name != `Prefect`)",
            where.toSql(),
        )
    }

    @Test
    fun `given where and and empty group then create the query statement`() {
        val where = where(age equalsTo 32) and { stmt ->
            stmt
        } or {
            emptyWhere()
        }

        Assertions.assertEquals("WHERE age = ?", where.toStmt())
        Assertions.assertEquals("WHERE age = `32`", where.toSql())
    }

    @Test
    fun `given where conditions when converting to soft delete then rearrange statements`() {
        val where = where(age equalsTo 32) or (age greaterThen 50)

        val sfWhere = where.getSoftDeleteStatement<User>()

        Assertions.assertEquals("WHERE (age = ? OR age > ?) AND deleted_at IS NULL", sfWhere.toStmt())
        Assertions.assertEquals("WHERE (age = `32` OR age > `50`) AND deleted_at IS NULL", sfWhere.toSql())
        Assertions.assertEquals(listOf(ColumnParam(age, 32), ColumnParam(age, 50)), where.params())
    }

    @Test
    fun `given an empty where when converting to soft delete then rearrange statements`() {
        val where = emptyWhere()

        val sfWhere = where.getSoftDeleteStatement<User>()

        Assertions.assertEquals("WHERE deleted_at IS NULL", sfWhere.toStmt())
    }

    @Test
    fun `given where conditions then build string value`() {
        val where = where(age equalsTo 32) or (age greaterThen 50) and (name differentOf "Arthur Dent")

        Assertions.assertEquals("WHERE age = `32` OR age > `50` AND name != `Arthur Dent`", where.toSql())
    }
}
