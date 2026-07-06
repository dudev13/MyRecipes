package com.br.application.payloads.responses

class TokenResponseFactory {

    fun create(successful: Boolean, message: String, token: String): TokenResponse {
        return TokenResponse(successful, message, token)
    }
}