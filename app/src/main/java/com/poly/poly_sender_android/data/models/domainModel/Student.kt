package com.poly.poly_sender_android.data.models.domainModel

import com.google.gson.annotations.SerializedName

data class Student (
    val id: Int,
    val name: String,
    val email: String,
    val attributes: AttributesItem
)