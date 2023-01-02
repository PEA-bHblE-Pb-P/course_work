package ru.ifmo.cs.domain.character

import kotlinx.serialization.Serializable

@Serializable
data class PeopleNearbyResponse(
    val id: Int,
    val name: String,
    val sex: String,
    val typeId: Int
)