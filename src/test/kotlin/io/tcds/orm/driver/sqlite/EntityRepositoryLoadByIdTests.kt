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
    private val addressRepository = EntityRepository(AddressTable(), connection())
    private val statusRepository = Repository(UserStatusTable(), connection())
    private val userRepository = EntityRepository(UserTable(addressRepository, statusRepository), connection())

    @BeforeEach
    override fun setup() {
        super.setup()

        connection().execute(
            "INSERT INTO addresses VALUES (?,?,?,?,?)",
            listOf(
                Param(addressRepository.table.id, "arthur-dent-address"),
                Param(addressRepository.table.street, "Galaxy Avenue"),
                Param(addressRepository.table.number, "124T"),
                Param(addressRepository.table.main, true),
                Param(addressRepository.table.createdAt, LocalDateTime.of(1995, Month.APRIL, 15, 9, 15, 33)),
            )
        )

        connection().execute(
            "INSERT INTO users VALUES (?,?,?,?,?,?,?)",
            listOf(
                Param(userRepository.table.id, "arthur-dent"),
                Param(userRepository.table.name, "Arthur Dent"),
                Param(userRepository.table.email, "arthur.dent@galaxy.org"),
                Param(userRepository.table.height, 1.78.toFloat()),
                Param(userRepository.table.age, 42),
                Param(userRepository.table.active, true),
                Param(userRepository.table.addressId, "arthur-dent-address"),
            )
        )

        connection().execute(
            "INSERT INTO user_status VALUES (?,?,?), (?,?,?)",
            listOf(
                // Status.ACTIVE
                Param(statusRepository.table.userId, "arthur-dent"),
                Param(statusRepository.table.status, Status.INACTIVE),
                Param(statusRepository.table.at, LocalDateTime.of(1995, Month.JANUARY, 10, 10, 10, 10)),

                // Status.INACTIVE
                Param(statusRepository.table.userId, "arthur-dent"),
                Param(statusRepository.table.status, Status.ACTIVE),
                Param(statusRepository.table.at, LocalDateTime.of(1995, Month.FEBRUARY, 11, 11, 11, 11)),
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
