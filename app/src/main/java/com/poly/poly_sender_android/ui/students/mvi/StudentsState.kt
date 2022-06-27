package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.State

data class StudentsState(
    //CreationAttribute
    val isLoading: Boolean,

    //CreationAttributeSelection
    val students: Set<Student>,
    val selectedStudents: Set<Student>,

    //CreationAttributeSelectionAttributing
    // Search Param // TODO pack to StudentsSearchParam like CreationAttributeSearchParam
    //val searchParam: CreationAttributeSearchParam,
    val searchAttributes: Set<Attribute>,
    val searchSelectedAttributes: Set<Attribute>,
    val searchSections: Set<Section>,
    val searchSelectedSection: Section?,
): State
