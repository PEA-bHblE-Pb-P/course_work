package ru.ifmo.cs.model

import org.jetbrains.exposed.sql.Table

object VampireServantsTable : Table("vampire_to_servant") {
    val servantId = reference("servant_id", Characters)
    val vampireId = reference("vampire_id", Characters)
    override val primaryKey = PrimaryKey(servantId, vampireId)
}