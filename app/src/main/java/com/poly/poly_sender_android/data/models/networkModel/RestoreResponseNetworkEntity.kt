package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class RestoreResponseNetworkEntity(

    @SerializedName("login")
    val login: String,

    @SerializedName("password")
    val password: String,

)