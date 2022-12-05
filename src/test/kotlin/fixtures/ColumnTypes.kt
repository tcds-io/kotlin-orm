package fixtures

import java.time.LocalDateTime

data class ColumnTypes(
    val integer: Int?,
    val double: Double?,
    val float: Float?,
    val long: Long?,
    val boolean: Boolean?,
    val datetime: LocalDateTime?,
    val enum: Status?,
    val string: String?,
)
