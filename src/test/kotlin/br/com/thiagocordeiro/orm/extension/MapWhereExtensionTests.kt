package br.com.thiagocordeiro.orm.extension

import br.com.thiagocordeiro.orm.clauses.Clause
import br.com.thiagocordeiro.orm.clauses.equalsTo
import br.com.thiagocordeiro.orm.type.IntegerColumn
import br.com.thiagocordeiro.orm.type.StringColumn
import fixtures.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MapWhereExtensionTests {
    private val idColumn = IntegerColumn<User>("id")
    private val nameColumn = StringColumn<User>("name")

    @Test
    fun `when order maps is empty then order statement is empty`() {
        val where = emptyList<Clause>()

        Assertions.assertEquals("", where.toWhereStatement())
    }

    @Test
    fun `when order maps is not empty then order statement has column order configuration`() {
        val where = listOf(
            idColumn equalsTo "123",
            nameColumn equalsTo "Arthur Dent"
        )

        Assertions.assertEquals("WHERE id = ? AND name = ?", where.toWhereStatement())
    }
}
