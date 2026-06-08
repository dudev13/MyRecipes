package com.br.application.payloads.requests

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class AddUserRequest(
    @SerializedName("nome")
    val nome: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
)
