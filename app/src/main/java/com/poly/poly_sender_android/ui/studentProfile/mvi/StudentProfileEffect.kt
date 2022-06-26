package com.poly.poly_sender_android.ui.studentProfile.mvi

import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.mvi.Effect

sealed interface StudentProfileEffect : Effect {
    object Loading : StudentProfileEffect
    data class Success(val student: Student) : StudentProfileEffect
    data class Failure(val errorMessage: String) : StudentProfileEffect
}
