package com.poly.poly_sender_android.ui.studentProfile.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer
import com.poly.poly_sender_android.ui.students.mvi.StudentsEffect
import com.poly.poly_sender_android.ui.students.mvi.StudentsNews

class StudentProfileReducer: Reducer<StudentProfileState, StudentProfileEffect, StudentProfileNews> {

    override fun reduce(state: StudentProfileState, effect: StudentProfileEffect): Pair<StudentProfileState?, StudentProfileNews?> {
        var reducedState: StudentProfileState? = null
        var reducedNews: StudentProfileNews? = null
        when (effect) {
            is StudentProfileEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }

            is StudentProfileEffect.Success -> {
                reducedState = state.copy(isLoading = false, student = effect.student)
            }
            is StudentProfileEffect.Failure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = StudentProfileNews.Message(effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}