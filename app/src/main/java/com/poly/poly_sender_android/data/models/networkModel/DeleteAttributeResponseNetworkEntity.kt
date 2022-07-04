package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class DeleteAttributeResponseNetworkEntity(
    @SerializedName("idAttribute")
    val idAttribute: String,
)