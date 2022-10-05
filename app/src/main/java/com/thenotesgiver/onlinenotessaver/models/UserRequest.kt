package com.thenotesgiver.onlinenotessaver.models

data class UserRequest(
    val email: String,
    val password: String,
    val username: String
)