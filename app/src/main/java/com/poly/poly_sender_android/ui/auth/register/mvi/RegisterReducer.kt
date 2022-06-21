package com.poly.poly_sender_android.ui.auth.register.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class RegisterReducer: Reducer<RegisterState, RegisterEffect, RegisterNews> {

    override fun reduce(state: RegisterState, effect: RegisterEffect): Pair<RegisterState?, RegisterNews?> {
        var reducedState: RegisterState? = null
        var reducedNews: RegisterNews? = null
        when (effect) {
            is RegisterEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }
            is RegisterEffect.Success -> {
                //TODO navigate to LoginFragment
            }
            is RegisterEffect.Failure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = RegisterNews.Message(effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}