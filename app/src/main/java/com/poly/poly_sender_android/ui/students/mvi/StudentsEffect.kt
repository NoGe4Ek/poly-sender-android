package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Effect
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeEffect

sealed interface StudentsEffect : Effect {
    object Loading : StudentsEffect

    //Success
    data class RefreshStudentsSuccess(val students: List<Student>) : StudentsEffect
    data class RefreshSearchingAttributesBySelectedSectionSuccess(val attributes: List<Attribute>) :
        StudentsEffect
    object ClearSearchParamSuccess : StudentsEffect

    //Failure
    data class RefreshStudentsFailure(val errorMessage: String) : StudentsEffect
    data class RefreshSearchingAttributesBySelectedSectionFailure(val errorMessage: String) :
        StudentsEffect
    data class ClearSearchParamFailure(val errorMessage: String) : StudentsEffect

    //Local Storage (only success)
    data class UpdateSharedStorageByStudentsSuccess(
        val students: List<Student>,
        val selectedStudents: List<Student>
    ) : StudentsEffect

    data class UpdateSharedStorageByStudentsAttributingSuccess(
        val searchAttributes: List<Attribute>,
        val searchSelectedAttributes: List<Attribute>,
        val searchSelectedSearchSection: String
    ) : StudentsEffect
}