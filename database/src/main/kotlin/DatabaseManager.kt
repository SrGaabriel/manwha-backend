package io.github.vibraplatform.database

import com.zaxxer.hikari.HikariDataSource
import io.github.vibraplatform.database.dao.Users
import io.github.vibraplatform.database.dao.WorkMappings
import io.github.vibraplatform.database.dao.Works
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseManager(
    private val postgreDatabaseConnection: PostgreDatabaseConnection,
) {
    fun connect() {
        val hikariDataSource = HikariDataSource().also { datasource ->
            with(postgreDatabaseConnection) {
                datasource.jdbcUrl = "jdbc:postgresql://${host}:${port}/${database}?useTimezone=true&serverTimezone=UTC"
                datasource.username = username
                datasource.password = password
            }
        }
        Database.connect(hikariDataSource)
    }

    fun createTables() = transaction {
        SchemaUtils.createMissingTablesAndColumns(
            Users,
            Works,
            WorkMappings
        )
    }
}