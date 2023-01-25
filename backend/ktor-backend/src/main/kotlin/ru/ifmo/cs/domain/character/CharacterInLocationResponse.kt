package ru.ifmo.cs.domain.character

import kotlinx.serialization.Serializable

@Serializable
data class CharacterInLocationResponse(
    val id: Int,
    val name: String,
    val sex: String,
    val typeId: Int,
    val bloodPercentage: Int
)