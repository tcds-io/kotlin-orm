package fixtures

import java.time.Instant

data class ColumnTypes(
    val long: Long,
    val json: Json,
    val enum: Status,
    val float: Float,
    val integer: Int,
    val double: Double,
    val string: String,
    val boolean: Boolean,
    val instant: Instant,
) {
    data class Json(val a: String, val b: Int)
}
