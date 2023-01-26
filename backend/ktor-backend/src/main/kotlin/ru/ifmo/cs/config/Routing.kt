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
import ru.ifmo.cs.domain.vampire.DrinkBloodRequest

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

        route("/servants") {
            get("/{id}") {
                call.respond(vampireService.getServants(call.parameters["id"]!!.toInt()))
            }
        }

        route("/location_type") {
            get("/{id}") {
                call.respond(locationService.locationType(call.parameters["id"]!!.toInt()))
            }
        }

        route("/favor") {
            get("/{id}") {
                call.respond(favorService.id(call.parameters["id"]!!.toInt()))
            }
        }

        route("/murder") {
            get("/all") {
                call.respond(murderService.all())
            }
        }

        route("/location") {
            get("/{id}") {
                call.respond(locationService.location(call.parameters["id"]!!.toInt()))
            }

            get("/all") {
                call.respond(locationService.locations())
            }

            post("/{location_id}/go") {
                val userSession = call.sessions.get<UserSession>()
                val id = userSession?.id!!
                val locationId = call.parameters["location_id"]!!.toInt()

                call.respond(locationService.go(id, locationId))
            }

            post("/{location_name}/go_by_name") {
                val userSession = call.sessions.get<UserSession>()
                val id = userSession?.id!!
                val locationName = call.parameters["location_name"]!!

                call.respond(locationService.goByName(id, locationName))
            }
        }

        post("/login/{id}") {
            call.sessions.set(UserSession(id = call.parameters["id"]!!.toInt()))
            call.respond(OK)
        }

        post("/logout") {
            call.sessions.clear<UserSession>()
            call.respond(OK)
        }

        route("/character") {
            get("/me") {
                val userSession = call.sessions.get<UserSession>()
                val id = userSession?.id!!
                call.respond(characterService.character(id))
            }

            get("/all") {
                call.respond(characterService.allCharacters())
            }

            get("/nearby") {
                val userSession = call.sessions.get<UserSession>()
                val id = userSession?.id!!
                call.respond(characterService.peopleNearby(id))
            }

            post("/{char_id}/kill") {
                val userSession = call.sessions.get<UserSession>()
                val id = userSession?.id!!
                val charId = call.parameters["char_id"]!!.toInt()
                val description = call.parameters["description"] ?: ""

                val result = characterService.kill(id, charId, description)

                if (result.isSuccess) {
                    call.respond(OK, result)
                } else {
                    call.respond(BadRequest, result.exceptionOrNull() ?: "Unknown error")
                }
            }
        }

        post("/hunter_go_to_for_fight/{location_id}") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id!!
            val locationId = call.parameters["location_id"]!!.toInt()

            call.respond(hunterService.goForFight(id, locationId))
        }

        post("/drink_blood") {
            val userSession = call.sessions.get<UserSession>()
            val id = userSession?.id!!
            val request = call.receive<DrinkBloodRequest>()
            if (vampireService.drinkBlood(id, request.charId, request.amount).isSuccess)
                call.respond(OK)
            else
                call.respond(BadRequest)
        }
    }
}

fun Application.configureApiDocumentation() {
    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml")
    }
}
