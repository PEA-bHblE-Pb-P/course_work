package ru.ifmo.cs.helios.s311693.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object LocationTypes : IntIdTable("location_type") {
    val type = varchar("type", 255)
}

class LocationType(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LocationType>(LocationTypes)

    var type by LocationTypes.type
}

@Serializable
data class LocationTypeResponse(
    val id: Int,
    val type: String,
)

fun LocationType.toResponse() = LocationTypeResponse(
    id = id.value,
    type = type
)
