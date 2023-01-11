package ru.ifmo.cs.domain.group

import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.model.Group

class GroupService {
    fun group(id: Int) = transaction { Group.findById(id) }!!.toResponse()

    fun all() = transaction { Group.all() }.map { it.toResponse() }.toTypedArray()
}