package com.poly.poly_sender_android.ui.filters.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class CreationFilterReducer: Reducer<CreationFilterState, CreationFilterEffect, CreationFilterNews> {

    override fun reduce(state: CreationFilterState, effect: CreationFilterEffect): Pair<CreationFilterState?, CreationFilterNews?> {
        var reducedState: CreationFilterState? = null
        var reducedNews: CreationFilterNews? = null
        when (effect) {
            is CreationFilterEffect.RefreshInProcess -> {
                reducedState = state.copy(isLoading = effect.isLoading)
            }

            is CreationFilterEffect.RefreshSuccess -> {
                reducedState = state.copy(isLoading = effect.isLoading, users = effect.users)
            }

            is CreationFilterEffect.RefreshFailure -> {
                reducedState = state.copy(isLoading = effect.isLoading)
                reducedNews = CreationFilterNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}