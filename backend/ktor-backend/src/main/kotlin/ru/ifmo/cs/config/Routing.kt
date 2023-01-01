package ru.ifmo.cs.config

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.domain.vampire.DrinkBloodRequest
import ru.ifmo.cs.model.PeopleNearby
import java.sql.ResultSet

fun Application.configureRouting(deps: Dependencies) = with(deps) {
    routing {
        get("/ping") {
            call.respondText("pong!")
        }

        route("/group") {
            get("/{id}") {
                call.respond(groupService.group(call.parameters["id"]!!.toInt()))
            }

            get {
                call.respond(groupService.all())
            }
        }

        route("/location_type") {
            get("/{id}") {
                call.respond(locationService.locationType(call.parameters["id"]!!.toInt()))
            }
        }

        route("/location") {
            get("/{id}") {
                call.respond(locationService.location(call.parameters["id"]!!.toInt()))
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
            val locationId = call.parameters["location_id"]!!.toInt()
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

        post("/drink_blood") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id!!
            val request = call.receive<DrinkBloodRequest>()
            call.respond(vampireService.drinkBlood(id, request.charId, request.amount))
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
