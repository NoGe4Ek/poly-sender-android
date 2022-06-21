package com.poly.poly_sender_android.data.models.networkModel

import com.google.gson.annotations.SerializedName

data class FilterNetworkEntity (
        @SerializedName("id")
        val id: Int,

        @SerializedName("filterName")
        val filterName: String,

        @SerializedName("mail")
        val mail: String,

        @SerializedName("expression")
        val expression: String?,

        @SerializedName("type")
        val type: String,

        @SerializedName("mode")
        val mode: String,

        @SerializedName("created")
        val created: String,

        @SerializedName("link")
        val link: String,

        @SerializedName("mailCounter")
        val mailCounter: String?,

        @SerializedName("students")
        val students: List<String>,

        @SerializedName("studentsDTO")
        val studentsDTO: List<String>,

        @SerializedName("status")
        val status: String,
)