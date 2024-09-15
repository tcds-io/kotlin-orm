package io.tcds.orm.connection

import io.tcds.orm.Param
import org.slf4j.Logger

interface ConnectionLogger {
    companion object {
        fun read(logger: Logger, sql: String, params: List<Param<*>>) {
            logger.info("ORM READ: ${sql.trim()} ${params(params)}")
        }

        fun write(logger: Logger, sql: String, params: List<Param<*>>) {
            logger.info("ORM WRITE: ${sql.trim()} ${params(params)}")
        }

        private fun params(params: List<Param<*>>): String {
            val list = params.mapIndexed { index, it -> "$index: ${it.name} = ${it.plain()}" }

            return "\n\t" + list.joinToString("\n\t")
        }
    }
}
