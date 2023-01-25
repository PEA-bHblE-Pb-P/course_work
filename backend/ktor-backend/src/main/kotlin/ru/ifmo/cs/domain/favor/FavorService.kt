package ru.ifmo.cs.domain.favor

import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.model.Favor

class FavorService {
    fun id(id: Int) = transaction { Favor.findById(id) }!!.toResponse()
}