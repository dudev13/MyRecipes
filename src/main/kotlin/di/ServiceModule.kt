package com.br.di

import com.br.domain.services.user.AddUserService
import com.br.infra.repository.user.UserReadOnlyRepository
import com.br.infra.repository.user.UserRepository
import com.br.infra.repository.user.UserWriteOnlyRepository
import org.koin.dsl.module

object ServiceModule {

    val module = module {
        single<AddUserService> { AddUserService(get(), get()) }
    }
}