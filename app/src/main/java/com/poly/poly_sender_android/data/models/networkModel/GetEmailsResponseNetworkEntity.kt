package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class GetEmailsResponseNetworkEntity(
    @SerializedName("idFilter")
    val idFilter: String,
)