package com.poly.poly_sender_android.data.models.domainModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val id: String,
    val filterName: String,
    val mail: String,
    val expression: String,
    val type: String,
    val mode: MailingMode,
    val created: String,
    val link: String,
    val mailCounter: String?,
    val students: List<String>,
    val studentsDTO: List<String>,
    val status: String
) : Parcelable