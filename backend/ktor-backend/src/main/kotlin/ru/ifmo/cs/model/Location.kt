package ru.ifmo.cs.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object LocationTable : IntIdTable("location") {
    val lat = float("lat")
    val lon = float("lon")
    val name = varchar("name", 255)
    val locationTypeId = integer("location_type_id").nullable()
}

class Location(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Location>(LocationTable)

    var lat by LocationTable.lat
    var lon by LocationTable.lon
    var name by LocationTable.name
    var locationTypeId by LocationTable.locationTypeId
}
