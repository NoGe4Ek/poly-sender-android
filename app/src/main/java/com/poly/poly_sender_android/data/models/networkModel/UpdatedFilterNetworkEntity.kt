package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class UpdatedFilterNetworkEntity(
    @SerializedName("idStaff")
    val idStaff: String,

    @SerializedName("idFilter")
    val idFilter: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("mailOption")
    val groupName: String,

    @SerializedName("expression")
    val expression: String?,

    @SerializedName("studentsId")
    val studentsId: List<String>,
)