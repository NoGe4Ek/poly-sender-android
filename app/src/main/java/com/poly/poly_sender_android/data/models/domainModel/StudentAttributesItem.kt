package com.poly.poly_sender_android.data.models.domainModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentAttributesItem(
    val attributeName: String,
    val attributeValues: List<String>,
): Parcelable