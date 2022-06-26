package com.poly.poly_sender_android.ui.auth.login.mvi

import android.app.Application
import android.content.Intent
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
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                App.appContext.startActivity(intent)
                App.mCurrentActivity.finish()
            }
            is LoginEffect.Failure -> {
                reducedState = state.copy(isLoading = false, password = "")
                reducedNews = LoginNews.Message(effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}