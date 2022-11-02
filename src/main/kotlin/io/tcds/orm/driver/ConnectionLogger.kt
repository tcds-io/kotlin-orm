package io.tcds.orm.driver

import io.tcds.orm.Param
import org.slf4j.Logger

interface ConnectionLogger {
    companion object {
        fun select(logger: Logger, sql: String, params: List<Param<*, *>>) {
            logger.info("ORM: ${sql.trim()} ${params(params)}")
        }

        fun execute(logger: Logger, sql: String, params: List<Param<*, *>>) {
            logger.info("ORM: ${sql.trim()} ${params(params)}")
        }

        private fun params(params: List<Param<*, *>>): String {
            val list = params.mapIndexed { index, it -> "$index: ${it.column.name} = ${it.value}" }

            return "\n\t" + list.joinToString("\n\t")
        }
    }
}
