package ru.ifmo.cs.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object Courts : IntIdTable("court") {
    val verdict = varchar("verdict", 255)
    val date = date("date")
    val defendantId = integer("defendant_id")
    val groupId = integer("group_id")
    val locationId = integer("location_id")
}

class Court(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Court>(Courts)

    var verdict by Courts.verdict
    var date by Courts.date
    var defendantId by Courts.defendantId
    var groupId by Courts.groupId
    var locationId by Courts.locationId
}
