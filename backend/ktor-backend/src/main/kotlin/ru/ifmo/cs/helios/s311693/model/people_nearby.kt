package ru.ifmo.cs.helios.s311693.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


object PeopleNearbyTable : IntIdTable("people_nearby") {
    val name = varchar("name", 255)
    val sex = varchar("sex", 255)
    val typeId = integer("type_id")
}

class PeopleNearby(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PeopleNearby>(PeopleNearbyTable)

    var name by PeopleNearbyTable.name
    var sex by PeopleNearbyTable.sex
    var typeId by PeopleNearbyTable.typeId
}

@Serializable
data class PeopleNearbyResponse(
    val id: Int,
    val name: String,
    val sex: String,
    val typeId: Int,
)

fun PeopleNearby.toResponse() = PeopleNearbyResponse(
    id = id.value,
    name = name,
    sex = sex,
    typeId = typeId,
)
