package com.poly.poly_sender_android.ui.auth.restore.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class RestoreReducer: Reducer<RestoreState, RestoreEffect, RestoreNews> {

    override fun reduce(state: RestoreState, effect: RestoreEffect): Pair<RestoreState?, RestoreNews?> {
        var reducedState: RestoreState? = null
        var reducedNews: RestoreNews? = null
        when (effect) {
            is RestoreEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }
            is RestoreEffect.Success -> {
                //TODO do we use response?
                //TODO navigate to LoginFragment or...?
            }
            is RestoreEffect.Failure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = RestoreNews.Message(effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}