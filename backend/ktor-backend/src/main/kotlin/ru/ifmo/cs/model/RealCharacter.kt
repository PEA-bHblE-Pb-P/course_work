package ru.ifmo.cs.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object Characters : IntIdTable("character") {
    val name = varchar("name", 255)
    val typeId = integer("type_id")
    val history = text("history")
    val sexId = reference("sex_id", SexTable)
    val locationId = integer("location_id")
    val birthday = date("birthday")
    val bloodPercentage = integer("blood_percentage")
    var photoUrl = text("photo_url").nullable()
}

class RealCharacter(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RealCharacter>(Characters)

    var name by Characters.name
    var typeId by Characters.typeId
    var history by Characters.history
    var sex by Sex referencedOn Characters.sexId
    var locationId by Characters.locationId
    var birthday by Characters.birthday
    var bloodPercentage by Characters.bloodPercentage
    var photoUrl by Characters.photoUrl
}
