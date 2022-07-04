package com.poly.poly_sender_android.data.models.domainModel

data class CreateFilterResponse(
    val idStaff: String,
    val idFilter: String?,
    val name: String,
    val mailOption: String,
    val expression: String?,
    val studentsId: List<String>,
)