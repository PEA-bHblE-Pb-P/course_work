package ru.ifmo.cs.util

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class DatabaseExtension: BeforeAllCallback {
    companion object {
        private const val DOCKER_IMAGE = "postgres:latest"
    }

    @Container
    private val container = PostgreSQLContainer(DOCKER_IMAGE)

    override fun beforeAll(context: ExtensionContext?) {
        container.start()

        val dataSource = hikari()

        Flyway.configure()
            .dataSource(dataSource)
            .load()
            .migrate()

        Database.connect(dataSource)
    }

    private fun hikari(): HikariDataSource {
        val hikariConfig = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = container.jdbcUrl
            username = container.username
            password = container.password
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        return HikariDataSource(hikariConfig)
    }
}