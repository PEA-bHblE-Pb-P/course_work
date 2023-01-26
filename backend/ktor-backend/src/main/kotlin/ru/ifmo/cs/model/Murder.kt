package ru.ifmo.cs.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.timestamp

object Murders : IntIdTable("murder") {
    val killerId = integer("killer_id")
    val victim = integer("victim")
    val description = text("description")
    val date = timestamp("date")
}

class Murder(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Murder>(Murders)

    var killerId by Murders.killerId
    var victim by Murders.victim
    var description by Murders.description
    var date by Murders.date
}
