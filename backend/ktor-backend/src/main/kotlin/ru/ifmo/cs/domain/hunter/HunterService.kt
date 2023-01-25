package ru.ifmo.cs.domain.hunter

import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.config.execAndMap
import ru.ifmo.cs.domain.court.CourtResponse
import ru.ifmo.cs.domain.court.toCourtResponse
import ru.ifmo.cs.model.Courts
import java.util.*

class HunterService {
    fun goForFight(id: Int, locationId: Int): List<CourtResponse> {
        val date = java.sql.Date(System.currentTimeMillis());
        val court = transaction {
            val query = "SELECT * FROM hunter_go_to_for_fight(?, ?)".trimIndent()
            val arguments = mutableListOf<Pair<ColumnType, *>>(
                Pair(IntegerColumnType(), id),
                Pair(IntegerColumnType(), locationId),
            )
            query.execAndMap(arguments) { }
            Courts.select { Courts.date.greater(date) }.limit(1).map { it.toCourtResponse() }
        }
        return court
    }
}