package ru.ifmo.cs.domain.location

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ru.ifmo.cs.domain.character.CharacterService
import ru.ifmo.cs.util.DatabaseExtension

@ExtendWith(DatabaseExtension::class)
class LocationServiceTest {
    private val service = LocationService()
    private val characterService = CharacterService()

    @Test
    fun location() {
        assertThat(service.location(1))
            .isEqualTo(
                LocationResponse(
                    id = 1,
                    lat = -41.29433f,
                    lon = 174.78569f,
                    name = "Genghis Khan House",
                    locationTypeId = 2
                )
            )
    }

    @Test
    fun locationType() {
        assertThat(service.locationType(1))
            .isEqualTo(LocationTypeResponse(id = 1, type = "bar"))
    }

    @Test
    fun go() {
        assertThat(characterService.character(1).location)
            .isNotEqualTo(3)

        service.go(1, 3)

        assertThat(characterService.character(1).location)
            .isEqualTo(3)
    }

    @Test
    fun goByName() {
        assertThat(characterService.character(1).location)
            .isNotEqualTo(1)

        service.goByName(1, "Genghis Khan House")

        assertThat(characterService.character(1).location)
            .isEqualTo(1)
    }
}