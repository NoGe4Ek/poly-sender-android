package com.poly.poly_sender_android.data.network

data class UpdateFilterBody(
    val idStaff: String,
    val idFilter: String,
    val name: String,
    val mailOption: String,
    val expression: String,
    val studentsId: List<String>,
)