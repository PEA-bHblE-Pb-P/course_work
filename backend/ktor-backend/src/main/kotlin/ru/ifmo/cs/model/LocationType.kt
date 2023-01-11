package ru.ifmo.cs.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object LocationTypes : IntIdTable("location_type") {
    val type = varchar("type", 255)
}

class LocationType(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LocationType>(LocationTypes)

    var type by LocationTypes.type
}
