package ru.ifmo.cs.domain.hunter

import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.config.execAndMap

class HunterService {
    fun goForFight(id: Int, locationId: Int) = transaction {
        val query = "SELECT * FROM hunter_go_to_for_fight(?, ?)".trimIndent()
        val arguments = mutableListOf<Pair<ColumnType, *>>(
            Pair(IntegerColumnType(), id),
            Pair(IntegerColumnType(), locationId),
        )
        query.execAndMap(arguments) { }
    }
}