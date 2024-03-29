package ru.ifmo.cs.domain.character

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import ru.ifmo.cs.util.DatabaseExtension

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ExtendWith(DatabaseExtension::class)
class CharacterServiceTest {
    private val characterService = CharacterService()

    @Test
    @Order(1)
    fun character() {
        assertThat(characterService.character(1))
            .isEqualTo(viagoCharacter())
    }

    @Test
    @Order(2)
    fun peopleNearby() {
        assertThat(characterService.peopleNearby(1).size)
            .isEqualTo(3)

        for (i in 1..5) {
            println(characterService.character(i))
            println(characterService.peopleNearby(i))
        }

        assertThat(characterService.peopleNearby(5).size)
            .isEqualTo(3)
    }

    @Test
    @Order(3)
    fun kill() {
        assertThat(characterService.kill(1, 5, "reasons"))
            .isEqualTo(Result.success(Unit))
    }

    @Test
    @Order(4)
    fun `kill should fail when victim is already dead`() {
        assertThat(characterService.kill(1, 5, "reasons"))
            .isNotEqualTo(Result.success(Unit))
    }

    @Test
    @Order(5)
    fun `kill should fail when killer eq victim`() {
        assertThat(characterService.kill(1, 1, "reasons"))
            .isNotEqualTo(Result.success(Unit))

        assertThat(characterService.character(1))
            .isEqualTo(viagoCharacter())
    }

    private fun viagoCharacter() = CharacterResponse(
        id = 1,
        name = "Виаго",
        typeId = 1,
        history = "Вампир трехсот семидесяти девяти лет от роду, родом из Германии. Денди, помешанный на чистоте и порядке.",
        sex = "masculine",
        birthday = "1636-03-09",
        location = 1,
        bloodPercentage = 100,
        photoUrl = "https://se.ifmo.ru/documents/1609903/1613524/photo_2021-09-17_16-47-58.jpg/f0afb842-d5e5-d0e4-215f-772e7a4312ba?t=1631886833350"
    )

    @Test
    @Order(6)
    fun `kill should fail when killer and victim have different locations`() {
        assertThat(characterService.kill(1, 3, "reasons"))
            .isNotEqualTo(Result.success(Unit))
    }
}