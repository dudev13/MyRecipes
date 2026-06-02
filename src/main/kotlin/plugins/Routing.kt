package com.br.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.Routing

fun Application.configureRouting() {
    install(Routing){

    }
}
