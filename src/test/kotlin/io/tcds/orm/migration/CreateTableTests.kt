package io.tcds.orm.migration

import fixtures.Address
import fixtures.UserStatus
import fixtures.UserTable
import io.mockk.mockk
import io.tcds.orm.EntityRepository
import io.tcds.orm.Repository
import org.junit.jupiter.api.Test

class CreateTableTests {
    private val addresses: EntityRepository<Address, String> = mockk()
    private val statusList: Repository<UserStatus> = mockk()

    @Test
    fun `given an orm table when it has columns then generate creation statement`() {
        val table = UserTable(addresses, statusList)

        // table.
    }
}
