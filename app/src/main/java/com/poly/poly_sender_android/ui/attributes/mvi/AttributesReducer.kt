package com.poly.poly_sender_android.ui.attributes.mvi

import android.widget.Toast
import com.poly.poly_sender_android.mvi.Reducer

class AttributesReducer: Reducer<AttributesState, AttributesEffect, AttributesNews> {

    override fun reduce(state: AttributesState, effect: AttributesEffect): Pair<AttributesState?, AttributesNews?> {
        var reducedState: AttributesState? = null
        var reducedNews: AttributesNews? = null
        when (effect) {
            is AttributesEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }

            is AttributesEffect.Success -> {
                reducedState = state.copy(isLoading = false, attributes = effect.attributes, searchParam = effect.searchParam)
            }

            is AttributesEffect.Failure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = AttributesNews.Message(effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}