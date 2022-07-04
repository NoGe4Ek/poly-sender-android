package com.poly.poly_sender_android.data.network

data class UpdateAttributeBody(
    val idStaff: String,
    val idAttribute: String,
    val name: String,
    val groupName: String,
    val expression: String,
    val studentsId: List<String>,
)