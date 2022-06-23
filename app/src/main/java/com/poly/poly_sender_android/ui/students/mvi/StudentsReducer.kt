package com.poly.poly_sender_android.ui.students.mvi

import com.poly.poly_sender_android.mvi.Reducer

class StudentsReducer :
    Reducer<StudentsState, StudentsEffect, StudentsNews> {

    override fun reduce(
        state: StudentsState,
        effect: StudentsEffect
    ): Pair<StudentsState?, StudentsNews?> {
        var reducedState: StudentsState? = null
        var reducedNews: StudentsNews? = null
        when (effect) {
            is StudentsEffect.Loading -> {
                reducedState = state.copy(isLoading = true)
            }

            is StudentsEffect.RefreshStudentsSuccess -> {
                reducedState = state.copy(isLoading = false)
                reducedState = state.copy(students = effect.students)
            }
            is StudentsEffect.RefreshStudentsFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = StudentsNews.Message(effect.errorMessage)
            }

            is StudentsEffect.RefreshSearchingAttributesBySelectedSectionSuccess -> {
                reducedState = state.copy(isLoading = false)
                reducedState = state.copy(searchAttributes = effect.attributes)
            }
            is StudentsEffect.RefreshSearchingAttributesBySelectedSectionFailure -> {
                reducedState = state.copy(isLoading = false)
                reducedNews = StudentsNews.Message(effect.errorMessage)
            }

            StudentsEffect.ClearSearchParamSuccess -> {
                reducedState = state.copy(searchSelectedAttributes = emptyList())
            }
            is StudentsEffect.ClearSearchParamFailure -> {
                reducedNews = StudentsNews.Message(effect.errorMessage)
            }

            is StudentsEffect.UpdateSharedStorageByStudentsAttributingSuccess -> {
                reducedState = state.copy(
                    searchAttributes = effect.searchAttributes,
                    searchSelectedAttributes = effect.searchSelectedAttributes,
                    searchSelectedSection = effect.searchSelectedSearchSection
                )
            }
            is StudentsEffect.UpdateSharedStorageByStudentsSuccess -> {
                reducedState = state.copy(
                    students = effect.students,
                    selectedStudents = effect.selectedStudents
                )
            }

        }
        return reducedState to reducedNews
    }
}