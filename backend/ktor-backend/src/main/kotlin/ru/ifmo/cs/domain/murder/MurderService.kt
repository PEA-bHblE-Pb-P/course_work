package ru.ifmo.cs.domain.murder

import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.model.Murder

class MurderService {
    fun all() = transaction { Murder.all().toList() }.map { it.toResponse() }.toTypedArray()
}