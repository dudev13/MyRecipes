package com.br.domain.validations

import com.br.application.payloads.requests.AuthUserRequest
import com.br.application.payloads.responses.TokenResponse
import com.br.utils.ErrorCodes
import com.br.utils.SuccessCodes

interface AuthUserRequestValidation {
    suspend fun validator(request: AuthUserRequest): TokenResponse
}

class AuthUserRequestValidationImpl(): AuthUserRequestValidation {


    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    override suspend fun validator(request: AuthUserRequest): TokenResponse {
        return when {
            request.email.isEmpty() -> TokenResponse(
                successful = false,
                message = ErrorCodes.EMAIL_REQUIRED.message
            )

            !emailRegex.matches(request.email) -> TokenResponse(
                successful = false,
                message = ErrorCodes.INVALID_EMAIL.message
            )

            request.password.isEmpty() -> TokenResponse(
                successful = false,
                message = ErrorCodes.PASSWORD_REQUIRED.message
            )

            request.password.length < 6 -> TokenResponse(
                successful = false,
                message = ErrorCodes.PASSWORD_TOO_SHORT.message
            )

            else -> TokenResponse(
                successful = true,
                message = SuccessCodes.VALID_REGISTRATION.message
            )
        }
    }
}
