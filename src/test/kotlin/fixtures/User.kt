package fixtures

import java.time.LocalDate

data class User(
    val id: String,
    val name: String,
    val email: String?,
    val height: Float?,
    val age: Int,
    val active: Boolean,
    val address: Address,
    val dateOfBirth: LocalDate,
)
