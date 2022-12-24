package ru.ifmo.cs.helios.s311693.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.locations.Location
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.sessions.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.helios.s311693.model.*
import java.sql.ResultSet

//
//@OptIn(KtorExperimentalLocationsAPI::class)
//@io.ktor.server.locations.Location("/kill/{char_id}")
//data class ListingKill(val char_id: String, val description: String)

@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.configureRouting() {
    install(Locations)
    routing {
        get("/ping") {
            call.respondText("pong!")
        }
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml")
        route("/group") {
            get("/{id}") {
                call.respond(transaction { Group.findById(call.parameters["id"]!!.toInt()) }!!.toResponse())
            }

            get {
                call.respond(transaction { Group.all() }.map { it.toResponse() }
                    .toTypedArray()) // cast to TypedArray for item type of array in swagger

            }
        }

        route("/location_type") {
            get("/{id}") {
                call.respond(transaction { LocationType.findById(call.parameters["id"]!!.toInt()) }!!.toResponse())
            }
        }
        route("/location") {
            get("/{id}") {
                call.respond(transaction { ru.ifmo.cs.helios.s311693.model.Location.findById(call.parameters["id"]!!.toInt()) }!!.toResponse())
            }
        }
        route("/login") {
            get("/{id}") {
                call.sessions.set(UserSession(id = call.parameters["id"]!!.toInt()))
                call.respond(HttpStatusCode.OK)
            }
        }

        get("/logout") {
            call.sessions.clear<UserSession>()
        }

        get("/people_nearby") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            fun ResultSet.toPeoplyNeabyResponse() = PeopleNearbyResponse(
                this.getInt("id"),
                this.getString("name"),
                this.getString("sex"),
                this.getInt("type_id")
            )
            call.respond(transaction {
                val query = "SELECT * FROM people_nearby(?)".trimIndent()
                val arguments = mutableListOf<Pair<ColumnType, *>>(
                    Pair(IntegerColumnType(), id),
                )
                query.execAndMap(arguments) { it.toPeoplyNeabyResponse() }
            })

        }

        get("/hunter_go_to_for_fight/{location_id}") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            val location_id = call.parameters["id"]!!.toInt()
            call.respond(transaction {
                val query = "SELECT * FROM hunter_go_to_for_fight(?, ?)".trimIndent()
                val arguments = mutableListOf<Pair<ColumnType, *>>(
                    Pair(IntegerColumnType(), id),
                    Pair(IntegerColumnType(), location_id),
                )
                query.execAndMap(arguments) { }
            })
        }

        get("/drink_blood/{char_id}/{amount}") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            val char_id = call.parameters["char_id"]!!.toInt()
            val amount = call.parameters["amount"]!!.toInt()
            call.respond(transaction {
                val query = "SELECT * FROM drink_blood(?, ?, ?)".trimIndent()
                val arguments = mutableListOf<Pair<ColumnType, *>>(
                    Pair(IntegerColumnType(), id),
                    Pair(IntegerColumnType(), char_id),
                    Pair(IntegerColumnType(), amount),
                )
                query.execAndMap(arguments) { }
            })
        }
        get("/go_to_location_by_id/{location_id}") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            val location_id = call.parameters["location_id"]!!.toInt()
            call.respond(transaction {
                val query = "SELECT * FROM go_to_location_by_id(?, ?)".trimIndent()
                val arguments = mutableListOf<Pair<ColumnType, *>>(
                    Pair(IntegerColumnType(), id),
                    Pair(IntegerColumnType(), location_id),
                )
                query.execAndMap(arguments) { }
            })
        }
        get("/go_to_location/{location_name}") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            val location_name = call.parameters["location_name"]!!.toInt()

            call.respond(transaction {
                val query = "SELECT * FROM go_to_location(?, ?)".trimIndent()
                val arguments = mutableListOf<Pair<ColumnType, *>>(
                    Pair(IntegerColumnType(), id),
                    Pair(VarCharColumnType(), location_name),
                )
                query.execAndMap(arguments) { }
            })
        }
//        get<ListingKill> { listing ->
        get("/kill/{char_id}") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id
            val char_id = call.parameters["char_id"]!!.toInt()
            val description = call.parameters["description"] ?: ""


            val (result, err) = transaction {
                try {
                    val query = "SELECT * FROM kill(?, ?, ?)".trimIndent()
                    val arguments = mutableListOf<Pair<ColumnType, *>>(
                        Pair(IntegerColumnType(), char_id),
                        Pair(IntegerColumnType(), id),
                        Pair(VarCharColumnType(), description),
                    )
                    return@transaction query.execAndMap(arguments) { } to null
                } catch (e: ExposedSQLException) {
//                    return@transaction null to e
                    return@transaction null to e
                }
            }
            if (err == null && result != null) {
                call.respond(HttpStatusCode.OK, result)
            } else {
                call.respond(HttpStatusCode.BadRequest, err?.getUsefulMessage() ?: "Unknown error")
            }
        }
    }
}
