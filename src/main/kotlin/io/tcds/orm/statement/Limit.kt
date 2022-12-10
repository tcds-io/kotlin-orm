package io.tcds.orm.statement

data class Limit constructor(private val limit: Int?, private val offset: Int?) {
    fun toStmt(): String {
        return when (limit) {
            null -> ""
            else -> "LIMIT $limit" + when (offset) {
                null -> ""
                else -> " OFFSET $offset"
            }
        }
    }
}
