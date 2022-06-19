package com.poly.poly_sender_android.ui.auth.login.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class LoginReducer: Reducer<LoginState, LoginEffect, LoginNews> {

    override fun reduce(state: LoginState, effect: LoginEffect): Pair<LoginState?, LoginNews?> {
        var reducedState: LoginState? = null
        var reducedNews: LoginNews? = null
        when (effect) {
            is LoginEffect.GetUserDetailsFailure -> {
                reducedNews = LoginNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is LoginEffect.GetUserDetailsSuccess -> {
                reducedState = state.copy(user = effect.user, friends = effect.friends)
            }
            is LoginEffect.ExternalCallFailure -> {
                reducedNews = LoginNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is LoginEffect.ExternalEmailFailure -> {
                reducedNews = LoginNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is LoginEffect.ExternalMapFailure -> {
                reducedNews = LoginNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is LoginEffect.ExternalSuccess -> {
                //Do nothing, that's ok
            }
        }
        return reducedState to reducedNews
    }
}