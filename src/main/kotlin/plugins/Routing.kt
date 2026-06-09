package com.br.plugins

import com.br.application.routes.userRoute
import com.br.domain.services.user.AddUserService
import io.ktor.server.application.*
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val addUserService by inject<AddUserService>()

    install(Routing){
        userRoute(addUserService)
    }
}
