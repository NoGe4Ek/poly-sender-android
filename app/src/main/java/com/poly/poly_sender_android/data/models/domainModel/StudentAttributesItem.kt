package com.poly.poly_sender_android.data.models.domainModel

data class StudentAttributesItem(
    val financing: List<String>,
    val form: List<String>,
    val programType: List<String>,
    val direction: List<String>,
    val orientation: List<String>,
    val targetedTraining: List<String>,
    val groupNumber: List<String>,
    val curse: List<String>,
    val sectionsWithAttributes: Map<String, List<String>>
)