package ru.ifmo.cs.domain.character

import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.config.execAndMap
import ru.ifmo.cs.model.RealCharacter
import java.sql.ResultSet

class CharacterService {
    fun character(id: Int) = transaction {
        RealCharacter.findById(id)!!.toResponse()
    }

    fun kill(killerId: Int, charId: Int, description: String): Result<Unit> {
        val (_, err) = transaction {
            try {
                val query = "SELECT * FROM kill(?, ?, ?)".trimIndent()
                val arguments = mutableListOf<Pair<ColumnType, *>>(
                    Pair(IntegerColumnType(), charId),
                    Pair(IntegerColumnType(), killerId),
                    Pair(VarCharColumnType(), description),
                )
                return@transaction query.execAndMap(arguments) { } to null
            } catch (e: ExposedSQLException) {
                return@transaction null to e
            }
        }

        return if (err == null)
            Result.success(Unit)
        else
            Result.failure(err)
    }

    fun peopleNearby(id: Int) = transaction {
        val query = "SELECT * FROM people_nearby(?)".trimIndent()
        val arguments = mutableListOf<Pair<ColumnType, *>>(
            Pair(IntegerColumnType(), id),
        )
        query.execAndMap(arguments) { it.toPeopleNearbyResponse() }
    }

    fun allCharacters() = transaction {
        RealCharacter.all().map { it.toResponse() }
    }

    private fun ResultSet.toPeopleNearbyResponse() = PeopleNearbyResponse(
        this.getInt("id"),
        this.getString("name"),
        this.getString("sex"),
        this.getInt("type_id")
    )
}