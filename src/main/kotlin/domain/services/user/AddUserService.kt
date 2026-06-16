package com.br.domain.services.user

import com.br.application.payloads.requests.AddUserRequest
import com.br.application.payloads.responses.SimpleResponse
import com.br.domain.entity.User
import com.br.domain.services.password.BCryptPasswordService
import com.br.domain.validations.AddValidationUserRequest
import com.br.infra.repository.user.UserReadOnlyRepository
import com.br.infra.repository.user.UserWriteOnlyRepository
import com.br.utils.Constants
import com.br.utils.ErrorCodes
import com.br.utils.SuccessCodes

class AddUserService(
    private val addValidationUserRequest: AddValidationUserRequest,
    private val bCryptPasswordService: BCryptPasswordService,
    private val userWriteOnlyRepository: UserWriteOnlyRepository,
    private val userReadOnlyRepository: UserReadOnlyRepository
) {

    suspend fun addUser(addUserRequest: AddUserRequest): SimpleResponse {

        val simpleResponse = addValidationUserRequest.validator(addUserRequest)
        if(!simpleResponse.successful) {
            return simpleResponse
        }

        if(userReadOnlyRepository.checkIfUserExists(addUserRequest.email)){
            return SimpleResponse(successful = false, message = ErrorCodes.EMAIL_ALREADY_USED.message)
        }

        val hashedPassword = bCryptPasswordService.hashedPassword(Constants.COST_FACTOR, addUserRequest.password)

        val user = User(
            name = addUserRequest.name,
            email = addUserRequest.email,
            password = hashedPassword,
            phone = addUserRequest.phone
        )

        val result = userWriteOnlyRepository.insertUser(user)

        return if(result){
            SimpleResponse(successful = true, message = SuccessCodes.REGISTRATION_COMPLETED.message)
        }else{
            SimpleResponse(successful = false, message = ErrorCodes.REGISTRATION_ERROR.message)
        }
    }
}