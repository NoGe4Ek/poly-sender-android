package com.poly.poly_sender_android.ui.auth.register.mvi

import androidx.navigation.Navigation
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.mvi.Reducer
import com.poly.poly_sender_android.ui.auth.register.RegisterFragmentDirections

class RegisterReducer: Reducer<RegisterState, RegisterEffect, RegisterNews> {

    override fun reduce(state: RegisterState, effect: RegisterEffect): Pair<RegisterState?, RegisterNews?> {
        var reducedState: RegisterState? = null
        var reducedNews: RegisterNews? = null
        when (effect) {
            is RegisterEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }
            is RegisterEffect.Success -> {
                val creationAttributeSelectionFragment =
                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                Navigation.findNavController(
                    App.mCurrentActivity,
                    R.id.nav_host_fragment_content_auth
                ).navigate(creationAttributeSelectionFragment)
            }
            is RegisterEffect.Failure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = RegisterNews.Message(effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}