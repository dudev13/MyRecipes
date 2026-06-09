package com.br.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = "myrecipes"

            validate { credential ->
                if (credential.payload.getClaim("email").asString() != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
