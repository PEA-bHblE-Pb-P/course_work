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
    val birthday: String,
    val location: Int?,
    val photoUrl: String?,
    val bloodPercentage: Int
)

fun RealCharacter.toResponse() = CharacterResponse(
    id = id.value,
    name = name,
    sex = sex.name,
    history = history,
    typeId = typeId,
    birthday = birthday.toString(),
    location = locationId,
    photoUrl = photoUrl,
    bloodPercentage = bloodPercentage
)