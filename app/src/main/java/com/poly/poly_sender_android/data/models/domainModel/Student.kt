package com.poly.poly_sender_android.data.models.domainModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    val id: String,
    val name: String,
    val email: String,
    val attributes: List<StudentAttributesItem>
) : Parcelable