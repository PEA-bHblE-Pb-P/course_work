package ru.ifmo.cs.helios.s311693.model

import kotlinx.serialization.Serializable
@Serializable
data class PeopleNearby(
    val id: Int,
    val name: String,
    val sex: String,
    val typeId: Int,
)
