package ru.ifmo.cs.domain.favor

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table.Dual.integer
import ru.ifmo.cs.model.Favor
import ru.ifmo.cs.model.Favors

@Serializable
data class FavorResponse(
    val id: Int,
    var sexId: Int,
    var ageMin: Int,
    var ageMax: Int
)

fun Favor.toResponse() = FavorResponse(
    id = id.value,
    sexId = sexId,
    ageMin = ageMin,
    ageMax = ageMax
)

fun ResultRow.toFavorResponse() = FavorResponse(
    this[integer("id")],
    this[Favors.sexId],
    this[Favors.ageMin],
    this[Favors.ageMax]
)
