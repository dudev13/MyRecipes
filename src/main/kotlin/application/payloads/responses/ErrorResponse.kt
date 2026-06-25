package com.br.application.payloads.responses

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("httpStatusCode")
    val httpStatusCode: Int,
)
