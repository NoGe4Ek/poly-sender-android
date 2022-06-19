package com.poly.poly_sender_android.ui.auth.restore.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class RestoreReducer: Reducer<RestoreState, RestoreEffect, RestoreNews> {

    override fun reduce(state: RestoreState, effect: RestoreEffect): Pair<RestoreState?, RestoreNews?> {
        var reducedState: RestoreState? = null
        var reducedNews: RestoreNews? = null
        when (effect) {
            is RestoreEffect.GetUserDetailsFailure -> {
                reducedNews = RestoreNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is RestoreEffect.GetUserDetailsSuccess -> {
                reducedState = state.copy(user = effect.user, friends = effect.friends)
            }
            is RestoreEffect.ExternalCallFailure -> {
                reducedNews = RestoreNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is RestoreEffect.ExternalEmailFailure -> {
                reducedNews = RestoreNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is RestoreEffect.ExternalMapFailure -> {
                reducedNews = RestoreNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is RestoreEffect.ExternalSuccess -> {
                //Do nothing, that's ok
            }
        }
        return reducedState to reducedNews
    }
}