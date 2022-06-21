package com.poly.poly_sender_android.data.models.domainModel

data class RestoreResponse(
    val login: String,
    val password: String //TODO can be only null or just return password?
)