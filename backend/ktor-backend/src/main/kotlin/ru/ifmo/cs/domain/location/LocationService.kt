package ru.ifmo.cs.domain.location

import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.model.Location
import ru.ifmo.cs.model.LocationType

class LocationService {
    fun location(id: Int) = transaction {
        LocationType.findById(id)
    }!!.toResponse()

    fun locationType(id: Int) = transaction {
        Location.findById(id)
    }!!.toResponse()
}