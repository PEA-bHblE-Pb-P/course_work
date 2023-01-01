package ru.ifmo.cs.model

import kotlinx.serialization.Serializable

@Serializable
data class PeopleNearby(
    val id: Int,
    val name: String,
    val sex: String,
    val typeId: Int,
)
