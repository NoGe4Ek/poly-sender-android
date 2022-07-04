package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class CreateFilterResponseNetworkEntity(
    @SerializedName("idStaff")
    val idStaff: String,

    @SerializedName("idFilter")
    val idFilter: String?,

    @SerializedName("name")
    val name: String,

    @SerializedName("mailOption")
    val mailOption: String,

    @SerializedName("expression")
    val expression: String?,

    @SerializedName("studentsId")
    val studentsId: List<String>,
)