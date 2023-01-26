package ru.ifmo.cs.domain.murder

import kotlinx.serialization.Serializable
import ru.ifmo.cs.model.Murder

@Serializable
data class MurderResponse(
    val id: Int,
    var killerId: Int,
    var victim: Int,
    var description: String,
    var date: String
)

fun Murder.toResponse() = MurderResponse(
    id = id.value,
    killerId = killerId,
    victim = victim,
    description = description,
    date = date.toString()
)
