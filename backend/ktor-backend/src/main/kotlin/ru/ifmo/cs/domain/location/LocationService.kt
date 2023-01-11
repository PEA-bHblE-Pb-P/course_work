package ru.ifmo.cs.domain.location

import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.config.execAndMap
import ru.ifmo.cs.domain.character.CharacterService
import ru.ifmo.cs.model.Location
import ru.ifmo.cs.model.LocationType

class LocationService(
    private val characterService: CharacterService
) {
    fun location(id: Int) = transaction {
        Location.findById(id)
    }!!.toResponse(characterService.vampiresCountByLocation(id))

    fun locations() = transaction {
        Location.all().toList()
    }.map { it.toResponse(characterService.vampiresCountByLocation(it.id.value)) }

    fun locationType(id: Int) = transaction {
        LocationType.findById(id)
    }!!.toResponse()

    fun go(id: Int, locationId: Int) = transaction {
        val query = "SELECT * FROM go_to_location_by_id(?, ?)".trimIndent()
        val arguments = mutableListOf<Pair<ColumnType, *>>(
            Pair(IntegerColumnType(), id),
            Pair(IntegerColumnType(), locationId),
        )
        query.execAndMap(arguments) { }
    }

    fun goByName(id: Int, locationName: String) = transaction {
        val query = "SELECT * FROM go_to_location(?, ?)".trimIndent()
        val arguments = mutableListOf<Pair<ColumnType, *>>(
            Pair(IntegerColumnType(), id),
            Pair(VarCharColumnType(), locationName),
        )
        query.execAndMap(arguments) { }
    }
}