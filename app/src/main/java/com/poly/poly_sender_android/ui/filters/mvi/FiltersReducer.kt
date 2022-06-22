package com.poly.poly_sender_android.ui.filters.mvi

import com.poly.poly_sender_android.mvi.Reducer

class FiltersReducer : Reducer<FiltersState, FiltersEffect, FiltersNews> {

    override fun reduce(
        state: FiltersState,
        effect: FiltersEffect
    ): Pair<FiltersState?, FiltersNews?> {
        var reducedState: FiltersState? = null
        var reducedNews: FiltersNews? = null
        when (effect) {
            FiltersEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }
            is FiltersEffect.Success -> {
                reducedState = state.copy(
                    isLoading = false,
                    filters = effect.filters,
                    filtersSearchParam = effect.filtersSearchParam
                )
            }
            is FiltersEffect.Failure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = FiltersNews.Message(effect.errorMessage)
            }
        }
        return reducedState to reducedNews
    }
}