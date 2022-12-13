package ru.ifmo.cs.helios.s311693.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.sessions.*
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ifmo.cs.helios.s311693.model.*
import java.sql.ResultSet

fun Application.configureRouting() {

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

        route("/login") {
            get("/{id}") {
                call.sessions.set(UserSession(id = call.parameters["id"]!!.toInt()))
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
    }
}
