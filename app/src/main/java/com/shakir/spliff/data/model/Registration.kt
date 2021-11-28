package com.shakir.spliff.data.model

data class Registration(
    val success: Success
) {
    data class Success(
        val token: String,
        val name: String

    )
}