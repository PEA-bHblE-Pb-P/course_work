package ru.ifmo.cs.helios.s311693.plugins

import com.typesafe.config.ConfigFactory
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.plugins.openapi.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.StatementType
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.ResultSet


object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = "jdbc:postgresql://localhost:5432/studs" //appConfig.property("db.jdbcUrl").getString()
    private val dbUser = "s311693" //appConfig.property("db.dbUser").getString()
    private val dbPassword = "s311693" //appConfig.property("db.dbPassword").getString()

    fun init() {
        Database.connect(hikari())
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dbUrl
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }

}

fun Application.configureDatabase() {
    DatabaseFactory.init()
}

// better context(Transaction)
fun <T : Any> String.execAndMap(
    args: Iterable<Pair<IColumnType, Any?>> = emptyList(),
    transform: (ResultSet) -> T
): List<T> {
    val result = arrayListOf<T>()
    TransactionManager.current().exec(this, args, StatementType.SELECT) { rs ->
        while (rs.next()) {
            result += transform(rs)
        }
    }
    return result
}