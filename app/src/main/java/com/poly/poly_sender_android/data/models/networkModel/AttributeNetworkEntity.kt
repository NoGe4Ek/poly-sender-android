package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class AttributeNetworkEntity(
    @SerializedName("id")
    val id: String,

    @SerializedName("owner")
    val owner: String,

    @SerializedName("attributeName")
    val attributeName: String,

    @SerializedName("groupName")
    val groupName: String,

    @SerializedName("expression")
    val expression: String?,

    @SerializedName("type")
    val type: String,

    @SerializedName("link")
    val link: String,

    @SerializedName("created")
    val created: String,

    @SerializedName("students")
    val students: List<String>,

    @SerializedName("studentsDTO")
    val studentsDTO: List<String>,

    @SerializedName("status")
    val status: String,
)