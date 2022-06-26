package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeEffect

sealed interface StudentsEffect : Effect {
    object Loading : StudentsEffect

    //Success
    data class RefreshStudentsSuccess(val students: Set<Student>) : StudentsEffect
    data class RefreshSearchingAttributesBySelectedSectionSuccess(val attributes: Set<Attribute>) :
        StudentsEffect
    object ClearSearchParamSuccess : StudentsEffect
    data class SelectStudentSuccess(val student: Student) : StudentsEffect
    data class DismissStudentSuccess(val student: Student) : StudentsEffect
    data class SelectAttributeSuccess(val attribute: Attribute) : StudentsEffect
    data class DismissAttributeSuccess(val attribute: Attribute) : StudentsEffect

    //Failure
    data class RefreshStudentsFailure(val errorMessage: String) : StudentsEffect
    data class RefreshSearchingAttributesBySelectedSectionFailure(val errorMessage: String) :
        StudentsEffect
    data class ClearSearchParamFailure(val errorMessage: String) : StudentsEffect
}