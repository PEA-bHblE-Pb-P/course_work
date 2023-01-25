package ru.ifmo.cs.domain.court

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table.Dual.integer
import ru.ifmo.cs.model.Court
import ru.ifmo.cs.model.Courts

@Serializable
data class CourtResponse(
    val id: Int,
    var verdict: String,
    var date: String,
    var defendantId: Int,
    var groupId: Int,
    var locationId: Int
)

fun Court.toResponse() = CourtResponse(
    id = id.value,
    verdict = verdict,
    date = date.toString(),
    defendantId = defendantId,
    groupId = groupId,
    locationId = locationId
)

fun ResultRow.toCourtResponse() = CourtResponse(
    this[integer("id")],
    this[Courts.verdict],
    this[Courts.date].toString(),
    this[Courts.defendantId],
    this[Courts.groupId],
    this[Courts.locationId]
)
