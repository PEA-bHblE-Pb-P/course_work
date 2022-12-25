package ru.ifmo.cs.helios.s311693.plugins

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.helios.s311693.model.*
import ru.ifmo.cs.helios.s311693.model.Location
import java.sql.ResultSet

fun Application.configureRouting() {
    routing {
        get("/ping") {
            call.respondText("pong!")
        }

        route("/group") {
            get("/{id}") {
                call.respond(transaction { Group.findById(call.parameters["id"]!!.toInt()) }!!.toResponse())
            }

            get {
                call.respond(
                    transaction { Group.all() }.map { it.toResponse() }
                        .toTypedArray()
                ) // cast to TypedArray for item type of array in swagger
            }
        }

        route("/location_type") {
            get("/{id}") {
                call.respond(transaction { LocationType.findById(call.parameters["id"]!!.toInt()) }!!.toResponse())
            }
        }
        route("/location") {
            get("/{id}") {
                call.respond(transaction { Location.findById(call.parameters["id"]!!.toInt()) }!!.toResponse())
            }
        }
        route("/login") {
            get("/{id}") {
                call.sessions.set(UserSession(id = call.parameters["id"]!!.toInt()))
                call.respond(OK)
            }
        }

        get("/logout") {
            call.sessions.clear<UserSession>()
        }

        get("/people_nearby") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            fun ResultSet.toPeopleNearbyResponse() = PeopleNearby(
                this.getInt("id"),
                this.getString("name"),
                this.getString("sex"),
                this.getInt("type_id")
            )
            call.respond(
                transaction {
                    val query = "SELECT * FROM people_nearby(?)".trimIndent()
                    val arguments = mutableListOf<Pair<ColumnType, *>>(
                        Pair(IntegerColumnType(), id),
                    )
                    query.execAndMap(arguments) { it.toPeopleNearbyResponse() }
                }
            )
        }

        get("/hunter_go_to_for_fight/{location_id}") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            val locationId = call.parameters["id"]!!.toInt()
            call.respond(
                transaction {
                    val query = "SELECT * FROM hunter_go_to_for_fight(?, ?)".trimIndent()
                    val arguments = mutableListOf<Pair<ColumnType, *>>(
                        Pair(IntegerColumnType(), id),
                        Pair(IntegerColumnType(), locationId),
                    )
                    query.execAndMap(arguments) { }
                }
            )
        }

        get("/drink_blood/{char_id}/{amount}") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            val charId = call.parameters["char_id"]!!.toInt()
            val amount = call.parameters["amount"]!!.toInt()
            call.respond(
                transaction {
                    val query = "SELECT * FROM drink_blood(?, ?, ?)".trimIndent()
                    val arguments = mutableListOf<Pair<ColumnType, *>>(
                        Pair(IntegerColumnType(), id),
                        Pair(IntegerColumnType(), charId),
                        Pair(IntegerColumnType(), amount),
                    )
                    query.execAndMap(arguments) { }
                }
            )
        }

        get("/go_to_location_by_id/{location_id}") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            val locationId = call.parameters["location_id"]!!.toInt()
            call.respond(
                transaction {
                    val query = "SELECT * FROM go_to_location_by_id(?, ?)".trimIndent()
                    val arguments = mutableListOf<Pair<ColumnType, *>>(
                        Pair(IntegerColumnType(), id),
                        Pair(IntegerColumnType(), locationId),
                    )
                    query.execAndMap(arguments) { }
                }
            )
        }

        get("/go_to_location/{location_name}") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            val locationName = call.parameters["location_name"]!!.toInt()

            call.respond(
                transaction {
                    val query = "SELECT * FROM go_to_location(?, ?)".trimIndent()
                    val arguments = mutableListOf<Pair<ColumnType, *>>(
                        Pair(IntegerColumnType(), id),
                        Pair(VarCharColumnType(), locationName),
                    )
                    query.execAndMap(arguments) { }
                }
            )
        }

        get("/kill/{char_id}") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            val charId = call.parameters["char_id"]!!.toInt()
            val description = call.parameters["description"] ?: ""

            val (result, err) = transaction {
                try {
                    val query = "SELECT * FROM kill(?, ?, ?)".trimIndent()
                    val arguments = mutableListOf<Pair<ColumnType, *>>(
                        Pair(IntegerColumnType(), charId),
                        Pair(IntegerColumnType(), id),
                        Pair(VarCharColumnType(), description),
                    )
                    return@transaction query.execAndMap(arguments) { } to null
                } catch (e: ExposedSQLException) {
                    return@transaction null to e
                }
            }
            if (err == null && result != null) {
                call.respond(OK, result)
            } else {
                call.respond(BadRequest, err?.getUsefulMessage() ?: "Unknown error")
            }
        }
    }
}

fun Application.configureApiDocumentation() {
    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml")
    }
}
