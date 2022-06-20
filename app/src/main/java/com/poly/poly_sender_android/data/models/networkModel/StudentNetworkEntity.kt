package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class StudentNetworkEntity (
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("attributes")
    val attributes: Map<String, List<String>>
)