package ru.ifmo.cs.domain.vampire

import kotlinx.serialization.Serializable

@Serializable
data class DrinkBloodRequest(val charId: Int, val amount: Int)