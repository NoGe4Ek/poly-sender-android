package com.poly.poly_sender_android.ui.studentProfile.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class StudentProfileReducer: Reducer<StudentProfileState, StudentProfileEffect, StudentProfileNews> {

    override fun reduce(state: StudentProfileState, effect: StudentProfileEffect): Pair<StudentProfileState?, StudentProfileNews?> {
        var reducedState: StudentProfileState? = null
        var reducedNews: StudentProfileNews? = null
        when (effect) {
            is StudentProfileEffect.GetUserDetailsFailure -> {
                reducedNews = StudentProfileNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is StudentProfileEffect.GetUserDetailsSuccess -> {
                reducedState = state.copy(user = effect.user, friends = effect.friends)
            }
            is StudentProfileEffect.ExternalCallFailure -> {
                reducedNews = StudentProfileNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is StudentProfileEffect.ExternalEmailFailure -> {
                reducedNews = StudentProfileNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is StudentProfileEffect.ExternalMapFailure -> {
                reducedNews = StudentProfileNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is StudentProfileEffect.ExternalSuccess -> {
                //Do nothing, that's ok
            }
        }
        return reducedState to reducedNews
    }
}