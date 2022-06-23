package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.State

data class StudentsState(
    //CreationAttribute
    val isLoading: Boolean,

    //CreationAttributeSelection
    val students: List<Student>,
    val selectedStudents: List<Student>,

    //CreationAttributeSelectionAttributing
    // Search Param // TODO pack to StudentsSearchParam like CreationAttributeSearchParam
    //val searchParam: CreationAttributeSearchParam,
    val searchAttributes: List<Attribute>,
    val searchSelectedAttributes: List<Attribute>,
    val searchSelectedSection: String, //TODO class sections
): State