package br.com.thiagocordeiro.orm

import br.com.thiagocordeiro.orm.clauses.*
import br.com.thiagocordeiro.orm.type.StringColumn
import fixtures.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ClausesTest {
    private val column = StringColumn<User>("name")

    @Test
    fun `name equals to`() {
        val clause1 = column equalsTo "123"
        val clause2 = column.equalsTo("345")

        Assertions.assertEquals("name = ?", clause1.toString())
        Assertions.assertEquals("name = ?", clause2.toString())
    }

    @Test
    fun `name like`() {
        val clause1 = column like "123"
        val clause2 = column.like("123")

        Assertions.assertEquals("name LIKE ?", clause1.toString())
        Assertions.assertEquals("name LIKE ?", clause2.toString())
    }

    @Test
    fun `is null`() {
        val clause = column.isNull()

        Assertions.assertEquals("name IS NULL", clause.toString())
    }

    @Test
    fun `is not null`() {
        val clause = column.isNotNull()

        Assertions.assertEquals("name IS NOT NULL", clause.toString())
    }

    @Test
    fun `is between`() {
        val clause = column.isBetween(1, 2)

        Assertions.assertEquals("name BETWEEN ? AND ?", clause.toString())
    }
}
