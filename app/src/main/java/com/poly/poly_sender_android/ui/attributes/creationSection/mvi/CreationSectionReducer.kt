package com.poly.poly_sender_android.ui.attributes.creationSection.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer
import com.poly.poly_sender_android.ui.attributes.mvi.CreationAttributeEffect
import com.poly.poly_sender_android.ui.attributes.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.mvi.CreationAttributeState

class CreationSectionReducer: Reducer<CreationAttributeState, CreationAttributeEffect, CreationAttributeNews> {

    override fun reduce(state: CreationAttributeState, effect: CreationAttributeEffect): Pair<CreationAttributeState?, CreationAttributeNews?> {
        var reducedState: CreationAttributeState? = null
        var reducedNews: CreationAttributeNews? = null
        when (effect) {
            is CreationAttributeEffect.RefreshInProcess -> {
                reducedState = state.copy(isLoading = effect.isLoading)
            }

            is CreationAttributeEffect.RefreshSuccess -> {
                reducedState = state.copy(isLoading = effect.isLoading, users = effect.users)
            }

            is CreationAttributeEffect.RefreshFailure -> {
                reducedState = state.copy(isLoading = effect.isLoading)
                reducedNews = CreationAttributeNews.Message(Toast.LENGTH_SHORT, effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}