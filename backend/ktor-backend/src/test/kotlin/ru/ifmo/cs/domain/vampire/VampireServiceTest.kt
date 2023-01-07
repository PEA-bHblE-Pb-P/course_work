package ru.ifmo.cs.domain.vampire

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import ru.ifmo.cs.util.DatabaseExtension

@ExtendWith(DatabaseExtension::class)
class VampireServiceTest {
    private val service = VampireService()

    @Test
    fun drinkBlood() {
        assertThat(service.drinkBlood(1, 3, 10))
            .isEqualTo(Result.success(Unit))
    }

    @Test
    fun `drinkBlood should fail when id eq char id`() {
        assertThatThrownBy {
            service.drinkBlood(1, 1, 10)
        }.isExactlyInstanceOf(org.jetbrains.exposed.exceptions.ExposedSQLException::class.java)
    }

    @Test
    fun `drinkBlood should fail for human`() {
        assertThatThrownBy {
            service.drinkBlood(3, 5, 10)
        }.isExactlyInstanceOf(org.jetbrains.exposed.exceptions.ExposedSQLException::class.java)
    }
}