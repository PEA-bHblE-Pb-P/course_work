package ru.ifmo.cs.domain.vampire

import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.config.execAndMap

class VampireService {
    fun drinkBlood(id: Int, charId: Int, amount: Int): Result<Unit> {
        transaction {
            val query = "SELECT * FROM drink_blood(?, ?, ?)".trimIndent()
            val arguments = mutableListOf<Pair<ColumnType, *>>(
                Pair(IntegerColumnType(), id),
                Pair(IntegerColumnType(), charId),
                Pair(IntegerColumnType(), amount),
            )
            query.execAndMap(arguments) { }
        }

        return Result.success(Unit)
    }
}