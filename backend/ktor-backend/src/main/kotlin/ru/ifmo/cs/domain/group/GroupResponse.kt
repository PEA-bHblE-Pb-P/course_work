package ru.ifmo.cs.domain.group

import kotlinx.serialization.Serializable
import ru.ifmo.cs.model.Group

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