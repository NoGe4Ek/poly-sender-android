package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class UpdatedAttributeNetworkEntity(
    @SerializedName("idStaff")
    val idStaff: String,

    @SerializedName("idAttribute")
    val idAttribute: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("groupName")
    val groupName: String,

    @SerializedName("expression")
    val expression: String?,

    @SerializedName("studentsId")
    val studentsId: List<String>,
)