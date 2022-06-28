package com.poly.poly_sender_android.ui.students.mvi

import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.State
import com.poly.poly_sender_android.ui.students.StudentsFragment

data class StudentsState(

    val label: String,

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
