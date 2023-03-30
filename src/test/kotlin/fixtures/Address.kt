package fixtures

import java.time.LocalDateTime

data class Address(
    val id: String,
    val street: String,
    val number: String,
    val main: Boolean,
    val createdAt: LocalDateTime,
) {
    fun updated(street: String? = null, number: String? = null, main: Boolean? = null) = Address(
        id = id,
        street = street ?: this.street,
        number = number ?: this.number,
        main = main ?: this.main,
        createdAt = createdAt,
    )
}
