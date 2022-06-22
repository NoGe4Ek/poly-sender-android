package com.poly.poly_sender_android.ui.auth.login.mvi

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.mvi.Reducer
import com.poly.poly_sender_android.ui.MainActivity

class LoginReducer : Reducer<LoginState, LoginEffect, LoginNews> {

    override fun reduce(state: LoginState, effect: LoginEffect): Pair<LoginState?, LoginNews?> {
        var reducedState: LoginState? = null
        var reducedNews: LoginNews? = null
        when (effect) {
            is LoginEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }
            is LoginEffect.Success -> {
                val intent = Intent(App.appContext, MainActivity::class.java).apply {
                    putExtra("user", effect.user.toString())
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                App.appContext.startActivity(intent)
            }
            is LoginEffect.Failure -> {
                reducedState = state.copy(isLoading = false, password = "")
                reducedNews = LoginNews.Message(effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}