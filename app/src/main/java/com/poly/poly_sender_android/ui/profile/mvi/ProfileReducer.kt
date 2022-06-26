package com.poly.poly_sender_android.ui.profile.mvi

import com.poly.poly_sender_android.mvi.Reducer

class ProfileReducer : Reducer<ProfileState, ProfileEffect, ProfileNews> {

    override fun reduce(
        state: ProfileState,
        effect: ProfileEffect
    ): Pair<ProfileState?, ProfileNews?> {
        var reducedState: ProfileState? = null
        var reducedNews: ProfileNews? = null
        when (effect) {
            is ProfileEffect.FetchUserFailure -> {
                reducedNews = ProfileNews.Message(effect.errorMessage)
            }
            is ProfileEffect.FetchUserSuccess -> {
                reducedState = state.copy(isLoading = false, user = effect.user)
            }
            ProfileEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }
        }
        return reducedState to reducedNews
    }
}