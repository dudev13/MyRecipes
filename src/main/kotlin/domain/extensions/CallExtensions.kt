package com.br.domain.extensions

import com.br.domain.entity.User
import com.br.domain.exceptions.UserAuthenticationNotFoundException
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authentication


fun ApplicationCall.getUserAuthentication(): String{
    val userModel = authentication.principal<User>()
    if(userModel != null){
        return userModel.id
    }else{
        throw UserAuthenticationNotFoundException("Não foi possível obter o usuário logado")
    }
}