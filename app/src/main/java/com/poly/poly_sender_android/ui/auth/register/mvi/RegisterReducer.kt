package com.poly.poly_sender_android.ui.auth.register.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class RegisterReducer: Reducer<RegisterState, RegisterEffect, RegisterNews> {

    override fun reduce(state: RegisterState, effect: RegisterEffect): Pair<RegisterState?, RegisterNews?> {
        var reducedState: RegisterState? = null
        var reducedNews: RegisterNews? = null
        when (effect) {
            is RegisterEffect.GetUserDetailsFailure -> {
                reducedNews = RegisterNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is RegisterEffect.GetUserDetailsSuccess -> {
                reducedState = state.copy(user = effect.user, friends = effect.friends)
            }
            is RegisterEffect.ExternalCallFailure -> {
                reducedNews = RegisterNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is RegisterEffect.ExternalEmailFailure -> {
                reducedNews = RegisterNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is RegisterEffect.ExternalMapFailure -> {
                reducedNews = RegisterNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is RegisterEffect.ExternalSuccess -> {
                //Do nothing, that's ok
            }
        }
        return reducedState to reducedNews
    }
}