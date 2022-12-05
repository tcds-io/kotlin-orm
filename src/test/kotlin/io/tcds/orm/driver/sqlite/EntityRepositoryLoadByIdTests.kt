package io.tcds.orm.driver.sqlite

import fixtures.*
import io.tcds.orm.EntityRepository
import io.tcds.orm.Param
import io.tcds.orm.Repository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

class EntityRepositoryLoadByIdTests : TestCase() {
    private val addressTable = AddressTable()
    private val statusTable = UserStatusTable()
    private val addressRepository = EntityRepository(addressTable, connection())
    private val statusRepository = Repository(statusTable, connection())

    private val userTable = UserTable(addressRepository, statusRepository)
    private val userRepository = EntityRepository(userTable, connection())

    @BeforeEach
    override fun setup() {
        super.setup()

        connection().execute(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                Param(addressTable.id, "arthur-dent-address"),
                Param(addressTable.street, "Galaxy Avenue"),
                Param(addressTable.number, "124T"),
                Param(addressTable.main, true),
                Param(addressTable.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )

        connection().execute(
            "INSERT INTO users VALUES (?,?,?,?,?,?,?)",
            listOf(
                Param(userTable.id, "arthur-dent"),
                Param(userTable.name, "Arthur Dent"),
                Param(userTable.email, "arthur.dent@galaxy.org"),
                Param(userTable.height, 1.78.toFloat()),
                Param(userTable.age, 42),
                Param(userTable.active, true),
                Param(userTable.addressId, "arthur-dent-address"),
            )
        )

        connection().execute(
            "INSERT INTO user_status VALUES (?,?,?), (?,?,?)",
            listOf(
                // Status.ACTIVE
                Param(statusTable.userId, "arthur-dent"),
                Param(statusTable.status, Status.INACTIVE),
                Param(statusTable.at, LocalDateTime.of(1995, Month.JANUARY, 10, 10, 10, 10)),

                // Status.INACTIVE
                Param(statusTable.userId, "arthur-dent"),
                Param(statusTable.status, Status.ACTIVE),
                Param(statusTable.at, LocalDateTime.of(1995, Month.FEBRUARY, 11, 11, 11, 11)),
            )
        )
    }

    @Test
    fun `given and user id when user exists then loadById returns an user entity`() {
        val id = "arthur-dent"

        val arthur = userRepository.loadById(id)

        Assertions.assertEquals(
            User(
                id = "arthur-dent",
                name = "Arthur Dent",
                email = "arthur.dent@galaxy.org",
                height = 1.78.toFloat(),
                age = 42,
                active = true,
                address = Address(
                    id = "arthur-dent-address",
                    street = "Galaxy Avenue",
                    number = "124T",
                    main = true,
                    createdAt = LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33),
                ),
                status = listOf(
                    UserStatus(
                        userId = "arthur-dent",
                        status = Status.INACTIVE,
                        at = LocalDateTime.of(1995, Month.JANUARY, 10, 10, 10, 10),
                    ),
                    UserStatus(
                        userId = "arthur-dent",
                        status = Status.ACTIVE,
                        at = LocalDateTime.of(1995, Month.FEBRUARY, 11, 11, 11, 11),
                    ),
                )
            ),
            arthur,
        )
    }

    @Test
    fun `given and user id when user does not exist then loadById returns null`() {
        val id = "another-user"

        val arthur = userRepository.loadById(id)

        Assertions.assertNull(arthur)
    }
}
