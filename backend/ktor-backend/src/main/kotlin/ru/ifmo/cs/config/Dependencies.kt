package ru.ifmo.cs.config

import ru.ifmo.cs.domain.character.CharacterService
import ru.ifmo.cs.domain.favor.FavorService
import ru.ifmo.cs.domain.group.GroupService
import ru.ifmo.cs.domain.hunter.HunterService
import ru.ifmo.cs.domain.location.LocationService
import ru.ifmo.cs.domain.murder.MurderService
import ru.ifmo.cs.domain.vampire.VampireService

class Dependencies {
    val groupService = GroupService()
    val characterService = CharacterService()
    val locationService = LocationService(characterService)
    val vampireService = VampireService()
    val favorService = FavorService()
    val murderService = MurderService()
    val hunterService = HunterService()
}