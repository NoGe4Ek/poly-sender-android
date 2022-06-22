package com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi

import com.poly.poly_sender_android.mvi.Reducer

class CreationAttributeReducer :
    Reducer<CreationAttributeState, CreationAttributeEffect, CreationAttributeNews> {

    override fun reduce(
        state: CreationAttributeState,
        effect: CreationAttributeEffect
    ): Pair<CreationAttributeState?, CreationAttributeNews?> {
        var reducedState: CreationAttributeState? = null
        var reducedNews: CreationAttributeNews? = null
        when (effect) {
            is CreationAttributeEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }
            CreationAttributeEffect.CreateAttributeSuccess -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationAttributeNews.Message("Attribute was successfully created")
                //TODO navigate to attribute fragment
            }
            is CreationAttributeEffect.CreateAttributeFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationAttributeNews.Message(effect.errorMessage)
            }

            is CreationAttributeEffect.RefreshStudentsSuccess -> {
                reducedState = state.copy(isLoading = false)
                reducedState = state.copy(students = effect.students)
            }
            is CreationAttributeEffect.RefreshStudentsFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationAttributeNews.Message(effect.errorMessage)
            }

            is CreationAttributeEffect.RefreshSearchingAttributesBySelectedSectionSuccess -> {
                reducedState = state.copy(isLoading = false)
                reducedState = state.copy(searchAttributes = effect.attributes)
            }
            is CreationAttributeEffect.RefreshSearchingAttributesBySelectedSectionFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = CreationAttributeNews.Message(effect.errorMessage)
            }

            CreationAttributeEffect.ClearSearchParamSuccess -> {
                reducedState = state.copy(searchSelectedAttributes = emptyList())
            }
            is CreationAttributeEffect.ClearSearchParamFailure -> {
                reducedNews = CreationAttributeNews.Message(effect.errorMessage)
            }

            is CreationAttributeEffect.UpdateSharedStorageByParamSuccess -> {
                reducedState = state.copy(
                    selectedName = effect.selectedName,
                    selectedSection = effect.selectedSection
                )
            }
            is CreationAttributeEffect.UpdateSharedStorageBySelectionAttributingSuccess -> {
                reducedState = state.copy(
                    searchAttributes = effect.searchAttributes,
                    searchSelectedAttributes = effect.searchSelectedAttributes,
                    searchSelectedSection = effect.searchSelectedSearchSection
                )
            }
            is CreationAttributeEffect.UpdateSharedStorageBySelectionSuccess -> {
                reducedState = state.copy(
                    students = effect.students,
                    selectedStudents = effect.selectedStudents
                )
            }

        }
        return reducedState to reducedNews
    }
}