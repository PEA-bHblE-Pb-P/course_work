package ru.ifmo.cs.domain.character

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import ru.ifmo.cs.util.DatabaseExtension

@ExtendWith(DatabaseExtension::class)
class CharacterServiceTest {
    private val characterService = CharacterService()

    @Test
    fun character() {
        assertThat(characterService.character(1))
            .isEqualTo(CharacterResponse(
                id = 1,
                name = "Виаго",
                typeId = 1,
                history = "Вампир трехсот семидесяти девяти лет от роду, родом из Германии. Денди, помешанный на чистоте и порядке.",
                sex = "masculine",
            ))
    }
}