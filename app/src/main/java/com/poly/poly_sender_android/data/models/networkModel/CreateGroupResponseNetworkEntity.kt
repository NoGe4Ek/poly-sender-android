package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class CreateGroupResponseNetworkEntity(
    @SerializedName("idStaff")
    val idStaff: String,

    @SerializedName("groupName")
    val groupName: String,
)