# Kotlin ORM


#### _Example of entities_

```kotlin
class User(
    val id: String,
    val name: String,
    val email: String,
    val height: Double,
    val age: Int,
    val active: Boolean,
    val address: Address,
)

class Address(
    val id: String,
    val street: String,
    val number: String,
    val main: Boolean,
    val createdAt: LocalDateTime,
)
```

Then you have to configure the mapping:
```kotlin
class UserTable(
    private val addresses: EntityRepository<Address, String>,
) : EntityTable<User, String>(table = "users", id = StringColumn("id") { it.id }) {
    val name = varchar("name") { it.name }
    val email = varchar("email") { it.email }
    val height = float("height") { it.height }
    val age = integer("age") { it.age }
    val active = bool("active") { it.active }
    val addressId = varchar("address_id") { it.address.id }

    override fun entity(row: OrmResultSet): User = User(
        id = row.get(id)!!,
        name = row.get(name)!!,
        email = row.get(email)!!,
        height = row.get(height)!!,
        age = row.get(age)!!,
        active = row.get(active)!!,
        address = addresses.loadById(row.get(addressId)!!)!!,
    )
}

class AddressTable : EntityTable<Address, String>(
    table = "addresses",
    id = StringColumn("id") { it.id },
) {
    val street = varchar("street") { it.street }
    val number = varchar("number") { it.number }
    val main = bool("main") { it.main }
    val createdAt = datetime("created_at") { it.createdAt }

    override fun entity(row: OrmResultSet): Address = Address(
        id = row.get(id)!!,
        street = row.get(street)!!,
        number = row.get(number)!!,
        main = row.get(main)!!,
        createdAt = row.get(createdAt)!!,
    )
}
```

Once the mapping is configured, a repository can be created to map database rows into the objects

```kotlin
val addressTable = AddressTable()
val repository = EntityRepository(addressTable, connection())

// load by id
val address = repository.loadById("address-id")

// delete entity
repository.delete(address)

// delete by conditions
val conditions = listOf(addressTable.main equalsTo true)
addressRepository.delete(conditions, conditions)
```
