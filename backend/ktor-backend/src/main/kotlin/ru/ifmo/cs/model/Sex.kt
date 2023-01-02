package ru.ifmo.cs.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Sexs: IntIdTable("sex") {
    val name = varchar("name", 50)
}

class Sex(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Sex>(Sexs)

    var name by Sexs.name
}