package fixtures

import java.time.LocalDateTime

data class Company(
    val id: String,
    val name: String,
    val status: Status,
    val employees: Int,
    val online: Boolean,
    val createdAt: LocalDateTime,
)
