package com.poly.poly_sender_android.data.models.domainModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attribute(
    val id: String,
    val owner: String,
    val attributeName: String,
    val groupName: String,
    val expression: String,
    val type: String,
    val link: String,
    val created: String,
    val students: List<String>,
    val studentsDTO: List<String>,
    val status: String,
) : Parcelable