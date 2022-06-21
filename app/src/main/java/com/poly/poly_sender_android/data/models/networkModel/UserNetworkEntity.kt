package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class UserNetworkEntity(
    @SerializedName("status")
    val status: String,

    @SerializedName("idStaff")
    val idStaff: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("fullName")
    val fullName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("roles")
    val roles: List<String>,
)