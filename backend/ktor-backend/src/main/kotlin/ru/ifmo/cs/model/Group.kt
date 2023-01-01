package ru.ifmo.cs.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Groups : IntIdTable("group") {
    val name = varchar("name", 255)
    val locationId = integer("location_id")
    val adminId = integer("admin_id")
}

class Group(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Group>(Groups)

    var name by Groups.name
    var locationId by Groups.locationId
    var adminId by Groups.adminId
}
