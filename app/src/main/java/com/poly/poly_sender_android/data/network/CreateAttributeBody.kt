package com.poly.poly_sender_android.data.network

data class CreateAttributeBody(
    val idStaff: String,
    val name: String,
    val groupName: String,
    val expression: String,
    val studentsId: List<String>,
)