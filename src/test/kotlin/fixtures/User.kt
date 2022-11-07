package fixtures

data class User(
    val id: String,
    val name: String,
    val email: String,
    val height: Double,
    val age: Int,
    val active: Boolean,
    val address: Address,
    val status: List<UserStatus>,
)
