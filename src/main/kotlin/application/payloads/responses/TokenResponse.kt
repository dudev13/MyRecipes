package com.br.application.payloads.responses

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("success")
    val successful: Boolean,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("token")
    val token: String? = null
)
