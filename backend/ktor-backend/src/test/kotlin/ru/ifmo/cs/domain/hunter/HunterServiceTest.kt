package ru.ifmo.cs.domain.hunter

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ru.ifmo.cs.util.DatabaseExtension

@ExtendWith(DatabaseExtension::class)
class HunterServiceTest {
    private val service = HunterService()

    @Test
    fun goForFight() {
        assertThat(service.goForFight(5, 1))
            .isNotEqualTo(listOf<Unit>())
    }

    @Test
    fun `goForFight should fail if not hunter character present`() {
        assertThatThrownBy {
            service.goForFight(1, 1)
        }.isExactlyInstanceOf(ExposedSQLException::class.java)
    }
}