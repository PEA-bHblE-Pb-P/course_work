package ru.ifmo.cs.helios.s311693.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

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

@Serializable
data class GroupResponse(
    val id: Int,
    val name: String,
    val locationId: Int,
    val adminId: Int
)

fun Group.toResponse() = GroupResponse(
    id = id.value,
    name = name,
    locationId = locationId,
    adminId = adminId
)
