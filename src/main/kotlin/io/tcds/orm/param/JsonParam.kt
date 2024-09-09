package io.tcds.orm.param

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.tcds.orm.Param
import java.sql.PreparedStatement

data class JsonParam<T>(
    override val name: String,
    override val value: T,
) : Param<T> {
    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(JavaTimeModule())
    }

    override fun plain(): String = mapper.writeValueAsString(value)
    override fun bind(stmt: PreparedStatement, index: Int) = stmt.setString(index, mapper.writeValueAsString(value))
}
