package com.br.di

import com.br.domain.services.password.BCryptPasswordService
import com.br.domain.services.token.TokenService
import com.br.domain.services.user.AddUserService
import com.br.domain.services.user.GetProfileUserService
import com.br.domain.services.user.GetUserByIdService
import com.br.domain.services.user.LoginUserService
import org.koin.dsl.module

object ServiceModule {

    val module = module {
        single<AddUserService> { AddUserService(get(), get(), get(), get())}
        single<BCryptPasswordService> { BCryptPasswordService() }
        single<LoginUserService> { LoginUserService(get(), get(), get(), get()) }
        single<TokenService> { TokenService() }
        single<GetProfileUserService>{ GetProfileUserService(get()) }
        single<GetUserByIdService>{ GetUserByIdService(get()) }
    }
}