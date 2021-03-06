package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Section
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeEffect

sealed interface StudentsEffect : Effect {
    object Loading : StudentsEffect

    //Success
    data class RefreshStudentsSuccess(val students: Set<Student>) : StudentsEffect
    data class RefreshSearchingAttributesBySelectedSectionSuccess(val attributes: Set<Attribute>) :
        StudentsEffect
    data class RefreshSectionsSuccess(val searchSections: Set<Section>) : StudentsEffect
    data class RefreshSelectedSectionSuccess(val searchSelectedSection: Section?) : StudentsEffect

    object ClearSearchParamSuccess : StudentsEffect
    data class SelectStudentSuccess(val student: String) : StudentsEffect
    data class DismissStudentSuccess(val student: String) : StudentsEffect
    data class SelectStudentsSuccess(val students: Set<String>) : StudentsEffect
    data class DismissStudentsSuccess(val students: Set<String>) : StudentsEffect
    data class SelectAttributeSuccess(val attribute: Attribute) : StudentsEffect
    data class DismissAttributeSuccess(val attribute: Attribute) : StudentsEffect

    //Failure
    data class RefreshStudentsFailure(val errorMessage: String) : StudentsEffect
    data class RefreshSearchingAttributesBySelectedSectionFailure(val errorMessage: String) :
        StudentsEffect
    data class RefreshSectionsFailure(val errorMessage: String) : StudentsEffect
    data class ClearSearchParamFailure(val errorMessage: String) : StudentsEffect
}