package fixtures

import java.time.LocalDate
import java.time.LocalDateTime

data class ColumnTypes(
    val integer: Int,
    val double: Double,
    val float: Float,
    val long: Long,
    val boolean: Boolean,
    val datetime: LocalDateTime,
    val date: LocalDate,
    val enum: Status,
    val string: String,
    val json: Data,
) {
    data class Data(val a: String, val b: Int)
}
