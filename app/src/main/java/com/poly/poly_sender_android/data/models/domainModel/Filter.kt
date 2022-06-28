package com.poly.poly_sender_android.data.models.domainModel

data class Filter(
    val id: String,
    val filterName: String,
    val mail: String,
    val expression: String?,
    val type: String,
    val mode: String,
    val created: String,
    val link: String,
    val mailCounter: String?,
    val students: List<String>,
    val studentsDTO: List<String>,
    val status: String
)