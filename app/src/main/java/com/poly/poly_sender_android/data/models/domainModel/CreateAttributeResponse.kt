package com.poly.poly_sender_android.data.models.domainModel

data class CreateAttributeResponse(
    val idStaff: String,
    val idAttribute: String?,
    val name: String,
    val groupName: String,
    val expression: String?,
    val studentsId: List<String>,
)