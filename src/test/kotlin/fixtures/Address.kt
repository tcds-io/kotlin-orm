package fixtures

import java.time.LocalDateTime

data class Address(
    val id: String,
    val street: String,
    val number: String,
    val main: Boolean,
    val createdAt: LocalDateTime,
)
