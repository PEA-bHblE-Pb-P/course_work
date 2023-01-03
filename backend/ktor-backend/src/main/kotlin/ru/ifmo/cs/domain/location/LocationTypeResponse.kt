package ru.ifmo.cs.domain.location

import kotlinx.serialization.Serializable
import ru.ifmo.cs.model.LocationType

@Serializable
data class LocationTypeResponse(
    val id: Int,
    val type: String,
)

fun LocationType.toResponse() = LocationTypeResponse(
    id = id.value,
    type = type
)
