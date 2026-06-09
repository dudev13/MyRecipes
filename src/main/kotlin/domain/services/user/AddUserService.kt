package com.br.domain.services.user

import com.br.application.payloads.requests.AddUserRequest
import com.br.application.payloads.responses.SimpleResponse
import com.br.domain.entity.User
import com.br.infra.repository.user.UserReadOnlyRepository
import com.br.infra.repository.user.UserWriteOnlyRepository
import com.br.utils.Constants
import com.br.utils.ErrorCodes
import com.br.utils.SuccessCodes

class AddUserService(
    private val UserWriteOnlyRepository: UserWriteOnlyRepository,
    private val UserReadOnlyRepository: UserReadOnlyRepository
) {

    suspend fun addUser(addUserRequest: AddUserRequest): SimpleResponse {

        val user = User(
            name = addUserRequest.name,
            email = addUserRequest.email,
            password = addUserRequest.password,
            phone = addUserRequest.phone
        )

        val result = UserWriteOnlyRepository.insertUser(user)

        return if(result){
            SimpleResponse(successful = true, message = SuccessCodes.REGISTRATION_COMPLETED.message)
        }else{
            SimpleResponse(successful = false, message = ErrorCodes.REGISTRATION_ERROR.message)
        }
    }
}