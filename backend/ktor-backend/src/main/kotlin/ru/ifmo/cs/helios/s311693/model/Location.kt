package ru.ifmo.cs.helios.s311693.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object LocationTable : IntIdTable("location_type") {
    val lat = float("lat")
    val lon = float("lon")
    val name = varchar("name", 255)
    val locationTypeId = integer("location_type_id")
}

class Location(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Location>(LocationTable)

    val lat by LocationTable.lat
    val lon by LocationTable.lon
    val name by LocationTable.name
    val locationTypeId by LocationTable.locationTypeId
}

@Serializable
data class LocationResponse(
    val id: Int,
    val lat: Float,
    val lon: Float,
    val name: String,
    val locationTypeId: Int,
)

fun Location.toResponse() = LocationResponse(
    id = id.value,
    lat = lat,
    lon = lon,
    name = name,
    locationTypeId = locationTypeId,
)
