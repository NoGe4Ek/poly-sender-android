package com.poly.poly_sender_android.data.network

data class CreateFilterBody(
    val idStaff: String,
    val name: String,
    val mailOption: String,
    val expression: String,
    val studentsId: List<String>,
)