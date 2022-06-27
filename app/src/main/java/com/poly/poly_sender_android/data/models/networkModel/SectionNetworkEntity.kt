package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class SectionNetworkEntity(
    @SerializedName("id")
    val id: String,

    @SerializedName("groupName")
    val groupName: String,

    @SerializedName("attributes")
    val attributes: List<String>,
)