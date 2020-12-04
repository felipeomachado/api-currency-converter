package br.com.felipeomachado

import br.com.felipeomachado.db.DatabaseInitializer
import br.com.felipeomachado.rest.Router
import org.jetbrains.exposed.sql.Database
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class Main {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(Main::class.java)
        private const val h2ConnectionString = "jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;"

        @JvmStatic
        fun main(args: Array<String>) {
            logger.info("H2 database connection string: $h2ConnectionString")
            val db = Database.connect(h2ConnectionString, driver = "org.h2.Driver")
            db.useNestedTransactions = true

            DatabaseInitializer.createSchemaAndTestData()

            Router(7000).start()
        }
    }
}