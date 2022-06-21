package com.poly.poly_sender_android.data.models.domainModel

data class User(
    val status: Boolean,
    val idStaff: String,
    val token: String,
    val fullName: String,
    val email: String,
    val roles: List<Role>,
)

enum class Role(val str: String) {
    USER("USER"),
    ADMIN("ADMIN")
}