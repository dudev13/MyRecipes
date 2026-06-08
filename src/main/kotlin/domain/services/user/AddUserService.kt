package com.br.domain.services.user

import com.br.application.payloads.requests.AddUserRequest
import com.br.domain.entity.User
import com.br.infra.repository.user.UserReadOnlyRepository
import com.br.infra.repository.user.UserWriteOnlyRepository

class AddUserService(
    private val UserWriteOnlyRepository: UserWriteOnlyRepository,
    private val UserReadOnlyRepository: UserReadOnlyRepository
) {

    fun addUser(addUserRequest: AddUserRequest) {

    }
}