package ru.ifmo.cs.config

import ru.ifmo.cs.domain.group.GroupService
import ru.ifmo.cs.domain.location.LocationService
import ru.ifmo.cs.domain.vampire.VampireService

class Dependencies {
    val groupService = GroupService()
    val locationService = LocationService()
    val vampireService = VampireService()
}