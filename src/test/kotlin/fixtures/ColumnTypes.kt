package fixtures

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class ColumnTypes(
    val long: Long,
    val json: Json,
    val date: Date,
    val enum: Status,
    val float: Float,
    val integer: Int,
    val double: Double,
    val string: String,
    val boolean: Boolean,
    val localdate: LocalDate,
    val localdatetime: LocalDateTime,
) {
    data class Json(val a: String, val b: Int)
}
