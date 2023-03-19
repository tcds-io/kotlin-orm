package io.tcds.orm.column

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.tcds.orm.Column
import java.sql.PreparedStatement

class JsonColumn<Entity, T>(name: String, value: (Entity) -> T) : Column<Entity, T>(name, value) {
    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(JavaTimeModule())
    }

    override fun bind(stmt: PreparedStatement, index: Int, value: T) {
        stmt.setString(index, mapper.writeValueAsString(value))
    }
}
