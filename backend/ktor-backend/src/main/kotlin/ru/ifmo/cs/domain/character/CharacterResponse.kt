package ru.ifmo.cs.domain.character

import kotlinx.serialization.Serializable
import ru.ifmo.cs.model.RealCharacter

@Serializable
data class CharacterResponse(
    val id: Int,
    val name: String,
    val sex: String,
    val typeId: Int,
    val history: String,
    val location: Int,
    val birthday: String,
    val bloodPercentage: Int
)

fun RealCharacter.toResponse() = CharacterResponse(
    id = id.value,
    name = name,
    sex = sex.name,
    history = history,
    typeId = typeId,
    location = locationId,
    birthday = birthday.toString(),
    bloodPercentage = bloodPercentage
)