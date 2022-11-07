package io.tcds.orm.statement

import fixtures.User
import io.tcds.orm.Param
import io.tcds.orm.column.IntegerColumn
import io.tcds.orm.column.StringColumn
import io.tcds.orm.extension.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StatementTests {
    private val name = StringColumn<User>("name") { it.name }
    private val age = IntegerColumn<User>("age") { it.age }

    @Test
    fun `given where conditions then create where statement with params`() {
        val where = where(age equalsTo 32) or (age greaterThen 50) and (name differentOf "Arthur Dent")

        Assertions.assertEquals("WHERE age = ? OR age > ? AND name != ?", where.toSql())
        Assertions.assertEquals(
            listOf(Param(age, 32), Param(age, 50), Param(name, "Arthur Dent")),
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
            where.toSql(),
        )
        Assertions.assertEquals(
            listOf(
                Param(age, 32),
                Param(age, 50),
                Param(name, "Arthur Dent"),
                Param(age, 100),
                Param(name, "Ford Prefect"),
            ),
            where.params(),
        )
    }
}
