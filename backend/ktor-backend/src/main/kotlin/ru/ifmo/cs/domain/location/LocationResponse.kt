package ru.ifmo.cs.domain.location

import kotlinx.serialization.Serializable
import ru.ifmo.cs.model.Location

@Serializable
data class LocationResponse(
    val id: Int,
    val lat: Float,
    val lon: Float,
    val name: String,
    val locationTypeId: Int?,
)

fun Location.toResponse() = LocationResponse(
    id = id.value,
    lat = lat,
    lon = lon,
    name = name,
    locationTypeId = locationTypeId,
)