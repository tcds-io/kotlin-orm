package io.tcds.orm.statement

enum class Operator(val operator: String) {
    NONE(""),
    WHERE("WHERE"),
    AND("AND"),
    OR("OR"),
}
