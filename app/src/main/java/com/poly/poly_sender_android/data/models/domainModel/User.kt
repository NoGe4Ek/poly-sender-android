package com.poly.poly_sender_android.data.models.domainModel

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class User constructor(
    val status: Boolean = true,
    val idStaff: String = "",
    val token: String = "",
    val fullName: String = "",
    val email: String = "",
    val roles: List<Role> = emptyList(),
) {
    constructor() : this(true, "", "", "", "", emptyList())
    constructor(status: Boolean) : this(status, "", "", "", "", emptyList())
    constructor(status: Boolean, idStaff: String) : this(status, idStaff, "", "", "", emptyList())
    constructor(status: Boolean, idStaff: String, token: String) : this(status, idStaff, token, "", "", emptyList())
    constructor(status: Boolean, idStaff: String, token: String, fullName: String) : this(status, idStaff, token, fullName, "", emptyList())
    constructor(status: Boolean, idStaff: String, token: String, fullName: String, email: String) : this(status, idStaff, token, fullName, email, emptyList())
}

enum class Role(val str: String) {
    USER("USER"),
    ADMIN("ADMIN")
}