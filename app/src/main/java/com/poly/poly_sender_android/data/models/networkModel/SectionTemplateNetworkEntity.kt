package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class SectionTemplateNetworkEntity(
    @SerializedName("groupName")
    val groupName: String,

    @SerializedName("idStaff")
    val idStaff: String,

    @SerializedName("idGroupName")
    val idGroupName: String,
)