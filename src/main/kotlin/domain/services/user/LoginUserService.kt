package com.br.domain.services.user

import com.br.application.payloads.requests.AuthUserRequest
import com.br.application.payloads.responses.TokenResponse
import com.br.domain.services.password.BCryptPasswordService
import com.br.domain.services.token.TokenService
import com.br.domain.validations.AuthUserRequestValidation
import com.br.infra.repository.user.UserReadOnlyRepository
import com.br.utils.ErrorCodes
import com.br.utils.SuccessCodes

class LoginUserService(
    private val tokenService: TokenService,
    private val bCryptPasswordService: BCryptPasswordService,
    private val authUserRequestValidation: AuthUserRequestValidation,
    private val userReadOnlyRepository: UserReadOnlyRepository
) {

    suspend fun loginUser(request: AuthUserRequest): TokenResponse {

        val tokenResponse = authUserRequestValidation.validator(request)
        if (!tokenResponse.successful) {
            return tokenResponse
        }

        val userModel = userReadOnlyRepository.checkIfUserExistsReturn(request.email)
            ?: return TokenResponse(successful = false, message = ErrorCodes.USER_EMAIL_NOT_FOUND.message)

        val hashedPassword = userModel.password
        val verifyPassword = bCryptPasswordService.verifyPassword(request.password.toCharArray(), hashedPassword)

        if(verifyPassword){
            val token = tokenService.generateToken(userModel.id)
            return TokenResponse(successful = true, message = SuccessCodes.LOGIN_SUCCESS.message, token = token)

        }else {
            return TokenResponse(successful = false, ErrorCodes.INCORRECT_PASSWORD.message)
        }

    }
}