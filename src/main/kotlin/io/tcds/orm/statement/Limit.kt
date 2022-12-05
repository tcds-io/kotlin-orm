package io.tcds.orm.statement

class Limit(private val limit: Int?, private val offset: Int?) {
    override fun toString(): String {
        return when (limit) {
            null -> ""
            else -> "LIMIT $limit" + when (offset) {
                null -> ""
                else -> " OFFSET $offset"
            }
        }
    }
}
