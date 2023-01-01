package ru.ifmo.cs.config

import ru.ifmo.cs.domain.group.GroupService
import ru.ifmo.cs.domain.location.LocationService

class Dependencies {
    val groupService = GroupService()
    val locationService = LocationService()
}