package fixtures

import java.time.LocalDateTime

data class UserStatus(val userId: String, val status: Status, val at: LocalDateTime)
