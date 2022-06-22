package com.poly.poly_sender_android.ui.attributes.creationSection.mvi

import com.poly.poly_sender_android.mvi.Reducer

class CreationSectionReducer: Reducer<CreationSectionState, CreationSectionEffect, CreationSectionNews> {

    override fun reduce(state: CreationSectionState, effect: CreationSectionEffect): Pair<CreationSectionState?, CreationSectionNews?> {
        var reducedState: CreationSectionState? = null
        var reducedNews: CreationSectionNews? = null
        when (effect) {
            is CreationSectionEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }

            is CreationSectionEffect.Success -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationSectionNews.Message("Section was successfully created")
                //TODO navigate to attribute fragment
            }

            is CreationSectionEffect.Failure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationSectionNews.Message(effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}