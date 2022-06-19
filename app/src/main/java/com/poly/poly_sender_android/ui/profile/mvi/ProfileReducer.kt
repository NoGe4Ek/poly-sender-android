package com.poly.poly_sender_android.ui.profile.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class ProfileReducer: Reducer<ProfileState, ProfileEffect, ProfileNews> {

    override fun reduce(state: ProfileState, effect: ProfileEffect): Pair<ProfileState?, ProfileNews?> {
        var reducedState: ProfileState? = null
        var reducedNews: ProfileNews? = null
        when (effect) {
            is ProfileEffect.GetUserDetailsFailure -> {
                reducedNews = ProfileNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is ProfileEffect.GetUserDetailsSuccess -> {
                reducedState = state.copy(user = effect.user, friends = effect.friends)
            }
            is ProfileEffect.ExternalCallFailure -> {
                reducedNews = ProfileNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is ProfileEffect.ExternalEmailFailure -> {
                reducedNews = ProfileNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is ProfileEffect.ExternalMapFailure -> {
                reducedNews = ProfileNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
            is ProfileEffect.ExternalSuccess -> {
                //Do nothing, that's ok
            }
        }
        return reducedState to reducedNews
    }
}