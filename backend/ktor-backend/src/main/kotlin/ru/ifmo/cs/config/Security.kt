package ru.ifmo.cs.config

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import java.io.File

data class UserSession(val id: Int = 0)

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<UserSession>("MY_SESSION", directorySessionStorage(File("build/.sessions"))) {
            cookie.extensions["SameSite"] = "lax"
        }
    }
}
