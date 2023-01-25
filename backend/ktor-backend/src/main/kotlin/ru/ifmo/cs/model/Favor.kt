package ru.ifmo.cs.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Favors : IntIdTable("favor") {
    val sexId = integer("sex_id")
    val ageMin = integer("age_min")
    val ageMax = integer("age_max")
}

class Favor(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Favor>(Favors)

    var sexId by Favors.sexId
    var ageMin by Favors.ageMin
    var ageMax by Favors.ageMax
}
