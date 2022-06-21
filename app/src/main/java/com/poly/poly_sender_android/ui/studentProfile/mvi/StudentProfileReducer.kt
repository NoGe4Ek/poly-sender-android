package com.poly.poly_sender_android.ui.studentProfile.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class StudentProfileReducer: Reducer<StudentProfileState, StudentProfileEffect, StudentProfileNews> {

    override fun reduce(state: StudentProfileState, effect: StudentProfileEffect): Pair<StudentProfileState?, StudentProfileNews?> {
        var reducedState: StudentProfileState? = null
        var reducedNews: StudentProfileNews? = null
        when (effect) {
            is StudentProfileEffect.Loading -> {
                // it's ok, you can implement necessary logic
            }
            is StudentProfileEffect.Success -> {
                // it's ok, you can implement necessary logic
            }
            is StudentProfileEffect.Failure -> {
                // it's ok, you can implement necessary logic
            }
        }
        return reducedState to reducedNews
    }
}