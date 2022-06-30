package com.poly.poly_sender_android.ui.attributes.mvi

import com.poly.poly_sender_android.mvi.Reducer

class AttributesReducer : Reducer<AttributesState, AttributesEffect, AttributesNews> {

    override fun reduce(
        state: AttributesState,
        effect: AttributesEffect
    ): Pair<AttributesState?, AttributesNews?> {
        var reducedState: AttributesState? = null
        var reducedNews: AttributesNews? = null
        when (effect) {
            is AttributesEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }

            is AttributesEffect.RefreshAttributesSuccess -> {
                reducedState = state.copy(isLoading = false, attributes = effect.attributes)
            }

            is AttributesEffect.RefreshAttributesFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = AttributesNews.Message(effect.errorMessage)
            }
            is AttributesEffect.RefreshSectionsFailure -> {
                reducedNews = AttributesNews.Message(effect.errorMessage)
            }
            is AttributesEffect.RefreshSectionsSuccess -> {
                reducedState = state.copy(searchSections = effect.searchSections)
            }
            is AttributesEffect.RefreshSelectedSectionSuccess -> {
                reducedState = state.copy(searchSelectedSection = effect.searchSelectedSection)
            }
        }
        return reducedState to reducedNews
    }
}