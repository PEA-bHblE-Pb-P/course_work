package ru.ifmo.cs.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.statements.StatementType
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgresql.util.PSQLException
import java.sql.ResultSet

object DatabaseFactory {
    fun hikari(): HikariDataSource {
        val hikariConfig = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = System.getenv("DB_URL")
            username = System.getenv("DB_USER")
            password = System.getenv("DB_PASSWORD")
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        return HikariDataSource(hikariConfig)
    }
}

fun configureDatabase() {
    Database.connect(DatabaseFactory.hikari())
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

fun ExposedSQLException.getUsefulMessage(): String {
    return (this.cause as PSQLException).serverErrorMessage?.message ?: ""
}