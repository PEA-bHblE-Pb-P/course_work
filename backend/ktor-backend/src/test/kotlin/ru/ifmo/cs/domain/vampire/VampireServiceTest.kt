package ru.ifmo.cs.domain.vampire

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import ru.ifmo.cs.util.DatabaseExtension

@ExtendWith(DatabaseExtension::class)
class VampireServiceTest {
    private val service = VampireService()

    @Test
    fun drinkBlood() {
        assertThat(service.drinkBlood(1, 1, 100)).isNotEqualTo(Unit)
    }
}